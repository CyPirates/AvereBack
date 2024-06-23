package CyPirates.avere.domain.unit.repository;

import CyPirates.avere.domain.unit.entity.DailyVisitCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyVisitRepository extends JpaRepository<DailyVisitCountEntity, Long> {
}
