package CyPirates.avere.domain.unit.repository;

import CyPirates.avere.domain.unit.entity.ReservationEntity;
import CyPirates.avere.domain.unit.entity.UnitEntity;
import CyPirates.avere.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("SELECT r FROM ReservationEntity r WHERE r.unit = :unit and r.status = 'WAITING'")
    List<ReservationEntity> findAllByUnit(UnitEntity unit);

    int countByUnitAndStatus(UnitEntity unit, ReservationEntity.Status status);

    // count the number of reservations for a unit that are in the waiting state and id is less than the given id
    int countByUnitAndStatusAndIdLessThan(UnitEntity unit, ReservationEntity.Status status, Long id);

    ReservationEntity findByUnitAndGuest(UnitEntity unit, User guest);

    @Query("SELECT r FROM ReservationEntity r WHERE r.id = :reservationId and r.guest.username = :username")
    Optional<ReservationEntity> findByIdAndGuestUsername(Long reservationId, String username);
}
