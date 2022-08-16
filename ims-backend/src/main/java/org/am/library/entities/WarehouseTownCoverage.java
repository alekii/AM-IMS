package org.am.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.library.entities.util.EntityConstants;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static org.am.library.entities.WarehouseTownCoverage.FK_TOWN_COLUMN_NAME;
import static org.am.library.entities.WarehouseTownCoverage.FK_WAREHOUSE_COLUMN_NAME;

@Entity 
@Table(name = WarehouseTownCoverage.WAREHOUSE_TOWN_COVERAGE_TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = {FK_WAREHOUSE_COLUMN_NAME, FK_TOWN_COLUMN_NAME}, name = WarehouseTownCoverage.FK_UNIQUE_WAREHOUSE_ID_AND_TOWN_ID)
})
@SequenceGenerator(name = WarehouseTownCoverage.SEQUENCE_GENERATOR_NAME, sequenceName = WarehouseTownCoverage.COUNTIES_SEQUENCE_ID_SEQ,
        allocationSize = EntityConstants.ALLOCATION_SIZE)
@DynamicUpdate
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseTownCoverage {

    protected static final String WAREHOUSE_TOWN_COVERAGE_TABLE_NAME = "warehouse_town_coverages";

    static final String ID_WAREHOUSE_TOWN_COVERAGE_COLUMN_NAME = "id_warehouse_town_coverage";
    protected static final String FK_TOWN_COLUMN_NAME = "fk_town";

    protected static final String TOWN_ID_RELATION_FOREIGN_KEY = "fk_warehouse_id";

    public static final String WAREHOUSE_COLUMN_NAME = "warehouse";
    
    protected static final String FK_WAREHOUSE_COLUMN_NAME = "fk_warehouse";
    
    protected static final String WAREHOUSE_ID_RELATION_FOREIGN_KEY = "fk_warehouse_id";

    protected static final String FK_UNIQUE_WAREHOUSE_ID_AND_TOWN_ID = "warehouse_town_coverage_town_unique_idx";

    protected static final String SEQUENCE_GENERATOR_NAME = "warehouse_town_coverages_sequence";

    protected static final String COUNTIES_SEQUENCE_ID_SEQ = "warehouse_town_coverages_id_seq";

    @Id
    @Column(name = ID_WAREHOUSE_TOWN_COVERAGE_COLUMN_NAME, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = FK_WAREHOUSE_COLUMN_NAME, foreignKey = @ForeignKey(name = WAREHOUSE_ID_RELATION_FOREIGN_KEY), nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = FK_TOWN_COLUMN_NAME, foreignKey = @ForeignKey(name = TOWN_ID_RELATION_FOREIGN_KEY), nullable = false)
    private Town town;

}
