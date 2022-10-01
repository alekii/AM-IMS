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
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = SupplierEntity.SUPPLIER_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = SupplierEntity.NAME_UNIQUE_INDEX_NAME, columnNames = EntityConstants.NAME_COLUMN_NAME),
                @UniqueConstraint(name = SupplierEntity.SID_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME),
                @UniqueConstraint(name = SupplierEntity.PHONE_NUMBER_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME),
                @UniqueConstraint(name = SupplierEntity.EMAIL_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME)
        }
)
public class SupplierEntity {

    private static final String SUPPLIER_SEQUENCE = "supplier_sequence";

    private static final String LEAD_TIME_COLUMN_NAME = "lead_time";

    private static final String ID_SUPPLIER_COLUMN_NAME = "id_supplier";

    private static final String EMAIL_COLUMN_NAME = "email";

    private static final String PHONE_NUMBER_COLUMN_NAME = "phone_number";

    static final String PHONE_NUMBER_UNIQUE_INDEX_NAME = "supplier_phone_number_unique_idx";

    static final String EMAIL_UNIQUE_INDEX_NAME = "supplier_email_unique_idx";

    static final String SUPPLIER_TABLE_NAME = "suppliers";

    static final String SUPPLIER_ID_SEQUENCE = "supplier_id_seq";

    static final String SID_UNIQUE_INDEX_NAME = "supplier_name_unique_idx";

    static final String NAME_UNIQUE_INDEX_NAME = "supplier_sid_unique_idx";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SUPPLIER_SEQUENCE)
    @SequenceGenerator(name = SupplierEntity.SUPPLIER_SEQUENCE, sequenceName = SupplierEntity.SUPPLIER_ID_SEQUENCE)
    @Column(name = ID_SUPPLIER_COLUMN_NAME, nullable = false)
    private Integer id;

    @Column(name = EntityConstants.SID_COLUMN_NAME, nullable = false, updatable = false)
    @Type(type = EntityConstants.PG_UUID)
    private UUID sid;

    @Column(name = EntityConstants.NAME_COLUMN_NAME, length = EntityConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @OneToMany(targetEntity = ProductEntity.class, fetch = FetchType.LAZY, mappedBy = EntityConstants.NAME_COLUMN_NAME)
    private List<ProductEntity> products;

    @OneToMany(targetEntity = PurchasesEntity.class, fetch = FetchType.LAZY, mappedBy = PurchasesEntity.SUPPLIER_COLUMN_NAME)
    private List<PurchasesEntity> purchases;

    @Column(name = LEAD_TIME_COLUMN_NAME)
    private int leadTime;

    @Column(name = PHONE_NUMBER_COLUMN_NAME)
    private String phoneNumber;

    @Column(name = EMAIL_COLUMN_NAME)
    private String email;
}
