package org.am.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.library.entities.util.EntityConstants;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = WarehouseEntity.WAREHOUSE_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = WarehouseEntity.NAME_UNIQUE_INDEX_NAME, columnNames = EntityConstants.NAME_COLUMN_NAME),
                @UniqueConstraint(name = WarehouseEntity.SID_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME)
        }
)
@SequenceGenerator(name = WarehouseEntity.WAREHOUSE_SEQUENCE, sequenceName = WarehouseEntity.WAREHOUSE_ID_SEQUENCE)
@Getter
@Setter
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseEntity {

    private static final int NAME_MAX_LENGTH = 50;

    private static final String WAREHOUSE_ADDRESS_FOREIGN_KEY = "fk_warehouse_address";

    private static final String CONTACT_NAME_COLUMN_NAME = "contact_name";

    private static final String PHONE_NUMBER_COLUMN_NAME = "phone_number";

    private static final String TRACKING_NUMBERS_COUNT_COLUMN_NAME = "tracking_numbers_count";

    private static final String ID_WAREHOUSE_COLUMN_NAME = "id_warehouse";

    static final String WAREHOUSE_TABLE_NAME = "warehouses";

    static final String NAME = "name";

    static final String NAME_UNIQUE_INDEX_NAME = "warehouse_sid_unique_idx";

    static final String SID_UNIQUE_INDEX_NAME = "warehouse_name_unique_idx";

    static final String WAREHOUSE_SEQUENCE = "warehouse_sequence";

    static final String WAREHOUSE_ID_SEQUENCE = "warehouse_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = WarehouseEntity.WAREHOUSE_SEQUENCE)
    @Column(name = ID_WAREHOUSE_COLUMN_NAME)
    private int id;

    @Column(name = NAME, length = NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Column(name = EntityConstants.SID_COLUMN_NAME, nullable = false, updatable = false)
    @Type(type = EntityConstants.PG_UUID)
    private UUID sid;

    @OneToOne(targetEntity = AddressEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = EntityConstants.ADDRESS_FOREIGN_KEY_COLUMN_NAME, foreignKey = @ForeignKey(name = WAREHOUSE_ADDRESS_FOREIGN_KEY))
    private AddressEntity address;

    @Column(name = CONTACT_NAME_COLUMN_NAME)
    private String contactName;

    @Column(name = PHONE_NUMBER_COLUMN_NAME)
    private String phoneNumber;

    @OneToMany(targetEntity = WarehouseTownCoverageEntity.class, fetch = FetchType.LAZY, mappedBy = WarehouseTownCoverageEntity.WAREHOUSE_COLUMN_NAME)
    private List<WarehouseTownCoverageEntity> warehouseTownCoverages;

    @Column(name = TRACKING_NUMBERS_COUNT_COLUMN_NAME)
    private Integer trackingNumbersCount;
}
