package CyPirates.avere.domain.unit.entity;

import CyPirates.avere.domain.item.entity.ItemEntity;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.user.entity.User;
import CyPirates.avere.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "unit")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UnitEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private ProgramEntity program;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> items;

    @Column(name = "unit_name", nullable = false)
    private String name;

    @Column(name = "unit_description", nullable = false)
    private String description;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyVisitCountEntity> dailyVisitCounts;

//    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ItemEntity> items;
}
