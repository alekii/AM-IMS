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
@Table(name = ImageEntity.IMAGE_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = ImageEntity.SID_UNIQUE_INDEX_NAME, columnNames = EntityConstants.SID_COLUMN_NAME)
        }
)
@Getter
@Setter
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ImageEntity {

    private static final String PRODUCT_FOREIGN_KEY_COLUMN_NAME = "fk_product";

    private static final String PRODUCT_IMAGE_FOREIGN_KEY = "fk_product_image";

    private static final String ID_IMAGE_COLUMN_NAME = "id_image";

    private static final String IMAGE_SEQUENCE = "image_sequence";

    private static final String IMAGE_ID_SEQUENCE = "image_id_seq";

    static final String SID_UNIQUE_INDEX_NAME = "image_sid_unique_index";

    static final String IMAGE_TABLE_NAME = "images";

    private static final String IMAGE_PATH_COLUMN_NAME = "image_path";

    public static final String IMAGE_COLUMN_NAME = "product";

    @Id
    @Column(name = ID_IMAGE_COLUMN_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IMAGE_SEQUENCE)
    @SequenceGenerator(name = IMAGE_SEQUENCE, sequenceName = IMAGE_ID_SEQUENCE)
    private int id;

    @Type(type = EntityConstants.PG_UUID)
    @Column(name = EntityConstants.SID_COLUMN_NAME)
    private UUID sid;

    @ManyToOne
    @JoinColumn(name = PRODUCT_FOREIGN_KEY_COLUMN_NAME, foreignKey = @ForeignKey(name = PRODUCT_IMAGE_FOREIGN_KEY))
    private ProductEntity product;

    @Column(name = IMAGE_PATH_COLUMN_NAME, nullable = false)
    private String imagePath;
}
