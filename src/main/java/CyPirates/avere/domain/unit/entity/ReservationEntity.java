package CyPirates.avere.domain.unit.entity;

import CyPirates.avere.domain.user.entity.User;
import CyPirates.avere.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity extends BaseEntity {
    // 방문자 상태
    public enum Status {
        WAITING, ACCEPTED, REJECTED
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private User guest;

    @Column(name = "staus", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
