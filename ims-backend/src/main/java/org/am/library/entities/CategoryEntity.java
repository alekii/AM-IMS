package org.am.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.library.entities.util.EntityConstants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Entity
@Table(name = CategoryEntity.CATEGORY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = CategoryEntity.NAME_UNIQUE_INDEX_NAME, columnNames = EntityConstants.NAME_COLUMN_NAME),
                @UniqueConstraint(name = CategoryEntity.SID_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME)
        }
)
@Getter
@Setter
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategoryEntity {

    private static final String CATEGORY_ID_SEQUENCE = "category_id_seq";

    private static final String ID_CATEGORY_COLUMN_NAME = "id_category";

    static final String SID_UNIQUE_INDEX_NAME = "category_name_unique_idx";

    static final String NAME_UNIQUE_INDEX_NAME = "category_sid_unique_idx";

    static final String CATEGORY_TABLE_NAME = "categories";

    static final String CATEGORY_SEQUENCE = "category_sequence";

    private static final int NAME_MAX_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = CATEGORY_SEQUENCE)
    @Column(name = ID_CATEGORY_COLUMN_NAME)
    @SequenceGenerator(name = CategoryEntity.CATEGORY_SEQUENCE, sequenceName = CategoryEntity.CATEGORY_ID_SEQUENCE)
    private Integer id;

    @Column(name = EntityConstants.SID_COLUMN_NAME, nullable = false, updatable = false)
    @Type(type = EntityConstants.PG_UUID)
    private UUID sid;

    @Column(name = EntityConstants.NAME_COLUMN_NAME, length = EntityConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;
}
