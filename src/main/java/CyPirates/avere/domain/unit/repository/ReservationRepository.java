package CyPirates.avere.domain.unit.repository;

import CyPirates.avere.domain.unit.entity.ReservationEntity;
import CyPirates.avere.domain.unit.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("SELECT r FROM ReservationEntity r WHERE r.unit = :unit and r.status = 'WAITING'")
    List<ReservationEntity> findAllByUnit(UnitEntity unit);

    ReservationEntity findByUnitAndGuest_Id(UnitEntity unit, Long guestId);
}
