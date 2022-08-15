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
@Table(name = Address.ADDRESS_TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = Address.ID_ADDRESS_UNIQUE_INDEX_NAME, columnNames = {Address.ID_ADDRESS_COLUMN_NAME})
} )
@SequenceGenerator(name = Address.SEQUENCE_GENERATOR_NAME, sequenceName = Address.SEQUENCE_GENERATOR_NAME)
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    static final String ADDRESS_TABLE_NAME = "addresses";

    static final String ID_ADDRESS_COLUMN_NAME = "id_address";

    private static final String STREET_NAME = "street_name";

    private static final String LATITUDE = "latitude";

    private static final String LONGITUDE = "longitude";

    private static final String MAP_URL_NAME = "map_url";

    private static final String TOWN_FOREIGN_KEY_COLUMN_NAME = "fk_town";

    private static final String TOWN_FOREIGN_KEY = "fk_address_town";

    static final String SEQUENCE_GENERATOR_NAME = "address_id_seq";

    static final String ID_ADDRESS_UNIQUE_INDEX_NAME = "addresses_unique_id_address";

    @Id
    @Column(name = ID_ADDRESS_COLUMN_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
    private Long id;

    @Column(name=STREET_NAME, nullable = false)
    private String street;

    @Column(name = LATITUDE)
    private Double latitude;

    @Column(name = LONGITUDE)
    private Double longitude;

    @Column(name = MAP_URL_NAME)
    private String mapUrl;

    @ManyToOne(targetEntity = Town.class, fetch = FetchType.LAZY)
    @JoinColumn(name = TOWN_FOREIGN_KEY_COLUMN_NAME, foreignKey = @ForeignKey(name = TOWN_FOREIGN_KEY))
    private Town town;
}
