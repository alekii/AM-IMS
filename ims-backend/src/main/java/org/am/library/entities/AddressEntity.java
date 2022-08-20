package org.am.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = AddressEntity.ADDRESS_TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = AddressEntity.ID_ADDRESS_UNIQUE_INDEX_NAME, columnNames = {AddressEntity.ID_ADDRESS_COLUMN_NAME})
})
@SequenceGenerator(name = AddressEntity.SEQUENCE_GENERATOR_NAME, sequenceName = AddressEntity.ADDRESS_SEQUENCE_ID_SEQ)
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class AddressEntity {

    private static final String STREET_NAME = "street_name";

    private static final String LATITUDE = "latitude";

    private static final String LONGITUDE = "longitude";

    private static final String MAP_URL_NAME = "map_url";

    private static final String TOWN_FOREIGN_KEY_COLUMN_NAME = "fk_town";

    private static final String TOWN_FOREIGN_KEY = "fk_address_town";

    static final String ADDRESS_TABLE_NAME = "addresses";

    static final String ID_ADDRESS_COLUMN_NAME = "id_address";

    static final String SEQUENCE_GENERATOR_NAME = "address_sequence";

    static final String ADDRESS_SEQUENCE_ID_SEQ = "address_id_seq";

    static final String ID_ADDRESS_UNIQUE_INDEX_NAME = "addresses_unique_id_address";

    @Id
    @Column(name = ID_ADDRESS_COLUMN_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
    private int id;

    @Column(name = STREET_NAME, nullable = false)
    private String street;

    @Column(name = LATITUDE)
    private Double latitude;

    @Column(name = LONGITUDE)
    private Double longitude;

    @Column(name = MAP_URL_NAME)
    private String mapUrl;

    @ManyToOne(targetEntity = TownEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = TOWN_FOREIGN_KEY_COLUMN_NAME, foreignKey = @ForeignKey(name = TOWN_FOREIGN_KEY))
    private TownEntity town;
}
