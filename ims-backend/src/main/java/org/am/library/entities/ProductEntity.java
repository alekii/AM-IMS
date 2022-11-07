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
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.am.library.entities.util.EntityConstants.PRICE_COLUMN_NAME;
import static org.am.library.entities.util.EntityConstants.WAREHOUSE_COLUMN_NAME;

@Entity
@Table(name = ProductEntity.PRODUCT_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = ProductEntity.NAME_UNIQUE_INDEX_NAME, columnNames = EntityConstants.NAME_COLUMN_NAME),
                @UniqueConstraint(name = ProductEntity.SID_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME)
        }
)
@SequenceGenerator(name = ProductEntity.PRODUCT_SEQUENCE, sequenceName = ProductEntity.PRODUCT_ID_SEQUENCE)
@Getter
@Setter
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductEntity {

    private static final String ID_PRODUCT_COLUMN_NAME = "id_product";

    private static final String PRODUCT_CATEGORY_FOREIGN_KEY = "fk_product_category";

    private static final String PRODUCT_DATE_RECEIVED_COLUMN_NAME = "date_received";

    private static final String PRODUCT_COLUMN_NAME = "name";

    private static final String PRODUCT_RECEIVED_BY = "received_by";

    private static final String PRODUCT_SKU = "sku";

    private static final String FK_SUPPLIER_COLUMN_NAME = "fk_supplier";

    private static final String PRODUCT_INVOICE_NUMBER = "invoice_number";

    private static final String PRODUCT_BRAND_FOREIGN_KEY = "fk_product_brand";

    private static final String PRODUCT_SUPPLIER_FOREIGN_KEY = "fk_product_supplier";

    private static final String DESCRIPTION_COLUMN_NAME = "description";

    private static final String IMAGE_PATH_COLUMN_NAME = "image_path";

    private static final String DISCOUNT_COLUMN_NAME = "discount";

    private static final String QUANTITY_COLUMN_NAME = "quantity";

    static final String PRODUCT_ID_SEQUENCE = "product_id_seq";

    static final String PRODUCT_TABLE_NAME = "products";

    static final String PRODUCT_SEQUENCE = "product_sequence";

    static final String NAME_UNIQUE_INDEX_NAME = "product_sid_unique_index";

    static final String SID_UNIQUE_INDEX_NAME = "product_name_unique_index";

    public static final String PRODUCTS_COLUMN_NAME = "products";

    public static final String BRAND_COLUMN_NAME = "brand";

    public static final String CATEGORY_COLUMN_NAME = "category";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PRODUCT_SEQUENCE)
    @Column(name = ID_PRODUCT_COLUMN_NAME)
    private int id;

    @Type(type = EntityConstants.PG_UUID)
    @Column(name = EntityConstants.SID_COLUMN_NAME)
    private UUID sid;

    @Column(name = PRODUCT_COLUMN_NAME, nullable = false)
    private String name;

    @Column(name = PRODUCT_DATE_RECEIVED_COLUMN_NAME)
    private Instant date_received;

    @ManyToOne
    @JoinColumn(name = EntityConstants.BRAND_FOREIGN_KEY_COLUMN_NAME, foreignKey = @ForeignKey(name = PRODUCT_BRAND_FOREIGN_KEY), nullable = false)
    private BrandEntity brand;

    @ManyToOne
    @JoinColumn(name = EntityConstants.CATEGORY_FOREIGN_KEY_COLUMN_NAME, foreignKey = @ForeignKey(name = PRODUCT_CATEGORY_FOREIGN_KEY), nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = FK_SUPPLIER_COLUMN_NAME, foreignKey = @ForeignKey(name = PRODUCT_SUPPLIER_FOREIGN_KEY), nullable = false)
    private SupplierEntity supplied_by;

    @Column(name = PRODUCT_RECEIVED_BY, nullable = false)
    private String received_by;

    @Column(name = PRODUCT_SKU, nullable = false)
    private String sku;

    @OneToMany(targetEntity = PurchaseProductEntity.class, fetch = FetchType.LAZY, mappedBy = PurchaseProductEntity.PRODUCT_COLUMN_NAME)
    private List<PurchaseProductEntity> lineItems;

    @Column(name = PRICE_COLUMN_NAME, nullable = false)
    private Double price;

    @Column(name = DISCOUNT_COLUMN_NAME)
    private Double discount;

    @Column(name = QUANTITY_COLUMN_NAME, nullable = false)
    private int quantity;

    @Column(name = DESCRIPTION_COLUMN_NAME)
    private String description;

    @OneToMany(targetEntity = ImageEntity.class, fetch = FetchType.LAZY, mappedBy = ImageEntity.IMAGE_COLUMN_NAME)
    private List<ImageEntity> images;

    @Column(name = WAREHOUSE_COLUMN_NAME)
    private UUID warehouseSid;
}
