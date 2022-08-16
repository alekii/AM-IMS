package org.am.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.library.entities.util.EntityConstants;

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
@Table(name = Warehouse.WAREHOUSE_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = Warehouse.NAME_SID_UNIQUE_INDEX_NAME,
                                  columnNames = {EntityConstants.NAME_COLUMN_NAME, EntityConstants.SID_COLUMN_NAME})
        }
)
@SequenceGenerator(name = Warehouse.WAREHOUSE_SEQUENCE, sequenceName = Warehouse.WAREHOUSE_ID_SEQUENCE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    static final String WAREHOUSE_TABLE_NAME = "warehouses";

    static final String NAME = "name";

    static final String NAME_SID_UNIQUE_INDEX_NAME = "warehouse_name_sid_unique_idx";

    static final String WAREHOUSE_SEQUENCE = "warehouse_sequence";

    static final String WAREHOUSE_ID_SEQUENCE = "warehouse_id_seq";

    private static final int NAME_MAX_LENGTH = 50;

    private static final String WAREHOUSE_ADDRESS_FOREIGN_KEY = "fk_warehouse_address";

    private static final String PHONE_NUMBER_COLUMN_NAME = "phone_number";


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Warehouse.WAREHOUSE_SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name=NAME, length = NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Column(name = EntityConstants.SID_COLUMN_NAME, nullable = false, updatable = false)
    private UUID sid;

    @OneToOne(targetEntity = Address.class, fetch = FetchType.LAZY)
    @JoinColumn(name = EntityConstants.ADDRESS_FOREIGN_KEY_COLUMN_NAME, foreignKey = @ForeignKey(name = WAREHOUSE_ADDRESS_FOREIGN_KEY))
    private Address warehouseAddress;

    @Column(name = PHONE_NUMBER_COLUMN_NAME)
    private String phoneNumber;

    @OneToMany(targetEntity = WarehouseTownCoverage.class, fetch = FetchType.LAZY, mappedBy = WarehouseTownCoverage.WAREHOUSE_COLUMN_NAME)
    private List<WarehouseTownCoverage> warehouseTownCoverages;

}
