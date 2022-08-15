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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = Town.TOWNS_TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = Town.SID, name = Town.TOWN_SID_UNIQUE_IDX)
})
@SequenceGenerator(name = Town.TOWN_SEQUENCE_NAME, sequenceName = Town.TOWN_SEQUENCE_ID_SEQ, allocationSize = Town.ALLOCATION_SIZE)
@DynamicUpdate
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Town {

    static final String TOWNS_TABLE_NAME = "towns";
    static final String ID_TOWN = "id_town";
    static final String SID = "sid";
    static final String NAME = "name";
    static final String TOWN_SID_UNIQUE_IDX = "towns_sid_unique_idx";

    static final String TOWN_SEQUENCE_ID_SEQ = "towns_id_seq";

    static final String TOWN_SEQUENCE_NAME = "towns_sequence";

    private static final String FK_COUNTY = "fk_county";

    private static final String FK_TOWNS_COUNTY = "fk_towns_county";

    public static final int ALLOCATION_SIZE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TOWN_SEQUENCE_NAME)
    @Column(name = ID_TOWN, nullable = false)
    private int id;

    @Column(name = SID, nullable = false)
    private UUID sid;

    @Column(name = NAME, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = FK_COUNTY, foreignKey = @ForeignKey(name = FK_TOWNS_COUNTY), nullable = false)
    private County county;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "town")
    private List<WarehouseTownCoverage> warehouseTownCoverages;

    public String getCountyName() {

        return getCounty().getName();
    }
}
