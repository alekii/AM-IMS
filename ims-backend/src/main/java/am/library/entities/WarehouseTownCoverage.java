package am.library.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class WarehouseTownCoverage {

    public static final String WAREHOUSE_COLUMN_NAME = "warehouse";
    private static final String FK_WAREHOUSE_COLUMN_NAME = "fk_warehouse";
    private static final String WAREHOUSE_FOREIGN_KEY = "fk_warehouse_id";

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = FK_WAREHOUSE_COLUMN_NAME, foreignKey = @ForeignKey(name = WAREHOUSE_FOREIGN_KEY), nullable = false)
    private Warehouse warehouse;

}
