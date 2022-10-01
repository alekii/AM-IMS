package org.am.library.entities;

import lombok.AllArgsConstructor;
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
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Entity
@Table(name = LineItemEntity.LINE_ITEM_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = LineItemEntity.SID_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME)
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LineItemEntity {

    private static final String FK_PRODUCT_COLUMN_NAME = "fk_product";

    private static final String PRODUCT_ID_RELATION_FOREIGN_KEY = "fk_product_id";

    private static final String PURCHASES_ID_RELATION_FOREIGN_KEY = "fk_purchases_id";

    private static final String FK_PURCHASES_COLUMN_NAME = "fk_purchases";

    private static final String QUANTITY_COLUMN_NAME = "quantity";

    private static final String ID_LINE_ITEM_COLUMN_NAME = "id_line_item";

    private static final String LINE_ITEM_SEQUENCE = "line_item_sequence";

    private static final String LINE_ITEM_ID_SEQUENCE = "line_item_id_seq";

    static final String LINE_ITEM_TABLE_NAME = "line_items";

    static final String SID_UNIQUE_INDEX_NAME = "line_item_sid_unique_idx";

    public static final String PURCHASES_COLUMN_NAME = "purchases";

    public static final String PRODUCT_COLUMN_NAME = "product";

    @Id
    @Column(name = ID_LINE_ITEM_COLUMN_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = LINE_ITEM_SEQUENCE)
    @SequenceGenerator(name = LineItemEntity.LINE_ITEM_SEQUENCE, sequenceName = LineItemEntity.LINE_ITEM_ID_SEQUENCE)
    private int id;

    @Column(name = EntityConstants.SID_COLUMN_NAME, nullable = false, updatable = false)
    @Type(type = EntityConstants.PG_UUID)
    private UUID sid;

    @ManyToOne
    @JoinColumn(name = FK_PRODUCT_COLUMN_NAME, foreignKey = @ForeignKey(name = PRODUCT_ID_RELATION_FOREIGN_KEY), nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = FK_PURCHASES_COLUMN_NAME, foreignKey = @ForeignKey(name = PURCHASES_ID_RELATION_FOREIGN_KEY), nullable = false)
    private PurchasesEntity purchases;

    @Column(name = QUANTITY_COLUMN_NAME, nullable = false)
    private int quantity;
}
