package org.am.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = CountyEntity.COUNTIES_TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = CountyEntity.SID, name = CountyEntity.COUNTIES_SID_UNIQUE_IDX)
})
@SequenceGenerator(name = CountyEntity.COUNTY_SEQUENCE_NAME, sequenceName = CountyEntity.COUNTIES_SEQUENCE_ID_SEQ)
@Getter
@Setter
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
public class CountyEntity {

    private static final String ID_COUNTY = "id_county";

    private static final String NAME = "name";

    static final String COUNTIES_TABLE_NAME = "counties";

    static final String SID = "sid";

    static final String COUNTY_SEQUENCE_NAME = "counties_sequence";

    static final String COUNTIES_SID_UNIQUE_IDX = "counties_sid_unique_idx";

    static final String COUNTIES_SEQUENCE_ID_SEQ = "counties_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = COUNTY_SEQUENCE_NAME)
    @Column(name = ID_COUNTY)
    private int id;

    @Column(name = SID, nullable = false)
    private UUID sid;

    @Column(name = NAME, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
    private List<TownEntity> towns;
}
