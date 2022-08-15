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

import static org.am.library.entities.Town.ALLOCATION_SIZE;

@Entity
@Table(name = County.COUNTIES_TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = County.SID, name = County.COUNTIES_SID_UNIQUE_IDX)
})
@SequenceGenerator(name = County.COUNTY_SEQUENCE_NAME, sequenceName = County.COUNTIES_SEQUENCE_ID_SEQ, allocationSize = ALLOCATION_SIZE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class County {
    static final String COUNTIES_TABLE_NAME = "counties";
    private static final String ID_COUNTY = "id_county";

    static final String SID = "sid";

    static final String COUNTY_SEQUENCE_NAME = "counties_sequence";

    static final String COUNTIES_SID_UNIQUE_IDX = "counties_sid_unique_idx";

    static final String COUNTIES_SEQUENCE_ID_SEQ = "counties_id_seq";

    private static final String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = COUNTY_SEQUENCE_NAME)
    @Column(name = ID_COUNTY)
    private int id;

    @Column(name = SID, nullable = false)
    private UUID sid;

    @Column(name = NAME, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
    private List<Town> towns;

}
