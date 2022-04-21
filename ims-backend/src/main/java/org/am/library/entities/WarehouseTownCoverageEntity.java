package org.am.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

import static org.am.library.entities.WarehouseTownCoverageEntity.FK_TOWN_COLUMN_NAME;
import static org.am.library.entities.util.EntityConstants.FK_WAREHOUSE_COLUMN_NAME;
import static org.am.library.entities.util.EntityConstants.WAREHOUSE_ID_RELATION_FOREIGN_KEY;

@Entity
@Table(name = WarehouseTownCoverageEntity.WAREHOUSE_TOWN_COVERAGE_TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = {FK_TOWN_COLUMN_NAME, FK_WAREHOUSE_COLUMN_NAME}, name = WarehouseTownCoverageEntity.FK_UNIQUE_WAREHOUSE_ID_AND_TOWN_ID)
})
@SequenceGenerator(name = WarehouseTownCoverageEntity.SEQUENCE_GENERATOR_NAME, sequenceName = WarehouseTownCoverageEntity.COUNTIES_SEQUENCE_ID_SEQ)
@DynamicUpdate
@Builder(builderClassName = "Builder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class
WarehouseTownCoverageEntity {

    protected static final String ID_TOWN_UNIQUE_INDEX_NAME = "town_unique_id";

    protected static final String WAREHOUSE_TOWN_COVERAGE_TABLE_NAME = "warehouse_town_coverages";

    protected static final String FK_TOWN_COLUMN_NAME = "fk_town";

    protected static final String TOWN_ID_RELATION_FOREIGN_KEY = "fk_warehouse_id";

    protected static final String FK_UNIQUE_WAREHOUSE_ID_AND_TOWN_ID = "warehouse_town_coverage_town_unique_idx";

    protected static final String SEQUENCE_GENERATOR_NAME = "warehouse_town_coverages_sequence";

    protected static final String COUNTIES_SEQUENCE_ID_SEQ = "warehouse_town_coverages_id_seq";

    static final String ID_WAREHOUSE_TOWN_COVERAGE_COLUMN_NAME = "id_warehouse_town_coverage";

    @Id
    @Column(name = ID_WAREHOUSE_TOWN_COVERAGE_COLUMN_NAME, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = FK_WAREHOUSE_COLUMN_NAME, foreignKey = @ForeignKey(name = WAREHOUSE_ID_RELATION_FOREIGN_KEY), nullable = false)
    private WarehouseEntity warehouse;

    @ManyToOne
    @JoinColumn(name = FK_TOWN_COLUMN_NAME, foreignKey = @ForeignKey(name = TOWN_ID_RELATION_FOREIGN_KEY), nullable = false)
    private TownEntity town;
}
