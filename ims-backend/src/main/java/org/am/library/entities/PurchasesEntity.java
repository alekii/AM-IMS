package org.am.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.library.entities.util.EntityConstants;
import org.am.library.entities.util.PurchaseStatus;
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

@Entity
@Table(name = PurchasesEntity.PURCHASES_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = PurchasesEntity.INVOICE_NUMBER_UNIQUE_INDEX_NAME, columnNames = PurchasesEntity.INVOICE_NUMBER_COLUMN_NAME),
                @UniqueConstraint(name = PurchasesEntity.SID_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME)
        }
)
@Getter
@Setter
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PurchasesEntity {

    private static final String PURCHASES_ID_SEQUENCE = "purchases_id_seq";

    private static final String DATE_RECEIVED_COLUMN_NAME = "date_received";

    private static final String BILL_VALUE = "bill_value";

    private static final String PURCHASE_STATUS = "purchase_status";

    private static final String SUPPLIER_ID_RELATION_FOREIGN_KEY = "fk_purchases_supplier";

    private static final String FK_SUPPLIER_COLUMN_NAME = "fk_supplier";

    static final String INVOICE_NUMBER_UNIQUE_INDEX_NAME = "purchases_invoice_number_unique_idx";

    static final String INVOICE_NUMBER_COLUMN_NAME = "invoice_number";

    static final String SID_UNIQUE_INDEX_NAME = "purchases_sid_unique_idx";

    static final String PURCHASES_TABLE_NAME = "purchases";

    static final String ID_PURCHASES_COLUMN_NAME = "id_purchases";

    private static final String PURCHASES_SEQUENCE = "purchases_sequence";

    public static final String SUPPLIER_COLUMN_NAME = "supplier";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PURCHASES_SEQUENCE)
    @SequenceGenerator(name = PurchasesEntity.PURCHASES_SEQUENCE, sequenceName = PurchasesEntity.PURCHASES_ID_SEQUENCE)
    @Column(name = ID_PURCHASES_COLUMN_NAME)
    private int id;

    @Column(name = EntityConstants.SID_COLUMN_NAME, nullable = false, updatable = false)
    @Type(type = EntityConstants.PG_UUID)
    private UUID sid;

    @ManyToOne
    @JoinColumn(name = FK_SUPPLIER_COLUMN_NAME, foreignKey = @ForeignKey(name = SUPPLIER_ID_RELATION_FOREIGN_KEY), nullable = false)
    private SupplierEntity supplier;

    @OneToMany(targetEntity = LineItemEntity.class, fetch = FetchType.LAZY, mappedBy = LineItemEntity.PURCHASES_COLUMN_NAME)
    private List<LineItemEntity> lineItems;

    @Column(name = INVOICE_NUMBER_COLUMN_NAME)
    private int invoiceNumber;

    @Column(name = DATE_RECEIVED_COLUMN_NAME)
    private Instant dateReceived;

    @Column(name = BILL_VALUE)
    private int billValue;

    @Column(name = PURCHASE_STATUS)
    private PurchaseStatus status;
}
