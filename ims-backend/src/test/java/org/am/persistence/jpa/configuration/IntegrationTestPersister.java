package org.am.persistence.jpa.configuration;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@TestComponent
public class IntegrationTestPersister {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public <E> E save(E entity) {

        return save(entity, new ArrayList<>());
    }

    private boolean isEntity(Class<?> collectionType) {

        return collectionType.isAnnotationPresent(Entity.class);
    }

    private Relationship parseRelationship(Class<?> entityClass, Field field) throws NoSuchFieldException {

        Relationship rel = new Relationship();

        rel.setParentType(entityClass);
        rel.setParent(field);

        // Get childType
        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        Class<?> childType = (Class<?>) stringListType.getActualTypeArguments()[0];

        rel.setChildType(childType);

        if (field.getAnnotation(OneToMany.class) != null) {
            rel.setChild(childType.getDeclaredField(field.getAnnotation(OneToMany.class).mappedBy()));
        } else if (field.getAnnotation(ManyToMany.class) != null) {
            if (!StringUtils.isBlank(field.getAnnotation(ManyToMany.class).mappedBy())) {
                rel.setChild(childType.getDeclaredField(field.getAnnotation(ManyToMany.class).mappedBy()));
            }
        }

        return rel;
    }

    private <E> E persist(E entity) {

        return entityManager.merge(entity);
    }

    @SuppressWarnings("unchecked")
    private <E> E save(E entity, List<Object> entitiesBeingPersisted) {

        Class<?> entityClass = entity.getClass();

        // Map of (field, collection) used to store all OneToMany entity relation
        // that should be persisted after the main entity.
        Map<Relationship, Collection<Object>> dependencies = new HashMap<>();

        // Prevent cycles
        if (entitiesBeingPersisted.contains(entity)) {
            return entity;
        }

        entitiesBeingPersisted.add(entity);

        Map<Field, Object> persistLater = new HashMap<>();

        Set<Field> fields = ReflectionUtils.getAllFields(entity.getClass(),
                                                         ReflectionUtils.withModifier(Modifier.PRIVATE | Modifier.PROTECTED));
        fields.forEach(field -> {
            try {
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {

                    field.setAccessible(true);
                    Object var = field.get(entity);

                    if (var != null) {
                        if (var instanceof Collection) {
                            Relationship rel = parseRelationship(entityClass, field);
                            if (isEntity(rel.getChildType())) {
                                dependencies.put(rel, (Collection<Object>) var);
                            }
                        } else if (entityClass.equals(var.getClass())) {
                            field.set(entity, null);
                            persistLater.put(field, var);
                        } else if (isEntity(var.getClass())) {
                            field.set(entity, save(var, entitiesBeingPersisted));
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        E persisted = persist(entity);
        entitiesBeingPersisted.remove(persisted);
        entitiesBeingPersisted.add(persisted);

        persistLater.forEach((field, var) -> {
            try {
                field.set(entity, save(var, entitiesBeingPersisted));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        dependencies.forEach((rel, children) -> {

            Collection<Object> persistedChildren = children.stream()
                    .peek(child -> ReflectionTestUtils.setField(child, rel.getChild().getName(), persisted))
                    .map(child -> save(child, entitiesBeingPersisted))
                    .collect(Collectors.toList());

            if (children instanceof Set) {
                persistedChildren = new HashSet<>(persistedChildren);
            }

            ReflectionTestUtils.setField(persisted, rel.getParent().getName(), persistedChildren);
        });

        entitiesBeingPersisted.remove(entity);

        return persisted;
    }
}
