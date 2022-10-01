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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Entity
@Table(name = BrandEntity.BRAND_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = BrandEntity.NAME_UNIQUE_INDEX_NAME, columnNames = EntityConstants.NAME_COLUMN_NAME),
                @UniqueConstraint(name = BrandEntity.SID_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME)
        }
)
@Getter
@Setter
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BrandEntity {

    private static final String BRAND_SEQUENCE = "brand_sequence";

    private static final String ID_BRAND_COLUMN_NAME = "id_brand";

    private static final String BRAND_ID_SEQUENCE = "brand_id_seq";

    private static final String PRODUCT_COLUMN_NAME = "product";

    static final String NAME_UNIQUE_INDEX_NAME = "brand_name_unique_idx";

    static final String SID_UNIQUE_INDEX_NAME = "brand_sid_unique_idx";

    static final String BRAND_TABLE_NAME = "brands";

    private static final int NAME_MAX_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = BRAND_SEQUENCE)
    @SequenceGenerator(name = BrandEntity.BRAND_SEQUENCE, sequenceName = BrandEntity.BRAND_ID_SEQUENCE)
    @Column(name = ID_BRAND_COLUMN_NAME)
    private Integer id;

    @Column(name = EntityConstants.SID_COLUMN_NAME, nullable = false, updatable = false)
    @Type(type = EntityConstants.PG_UUID)
    private UUID sid;

    @Column(name = EntityConstants.NAME_COLUMN_NAME, length = EntityConstants.NAME_MAX_LENGTH, nullable = false)
    String name;

    @OneToMany(targetEntity = ProductEntity.class, fetch = FetchType.LAZY, mappedBy = BrandEntity.PRODUCT_COLUMN_NAME)
    private ProductEntity product;
}
