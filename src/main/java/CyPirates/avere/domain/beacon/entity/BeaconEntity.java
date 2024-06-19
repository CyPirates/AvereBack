package CyPirates.avere.domain.beacon.entity;

import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "beacon")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BeaconEntity extends BaseEntity {
    @Column(name = "beacon_uuid", nullable = false, unique = true)
    private String beaconUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private ProgramEntity program;
}
