package org.am.persistence.jpa.configuration;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class Relationship {

    private Class<?> parentType;

    private Field parent;

    private Class<?> childType;

    private Field child;
}
