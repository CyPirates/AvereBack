package CyPirates.avere.domain.unit.repository;

import CyPirates.avere.domain.item.entity.ItemEntity;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.unit.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    List<UnitEntity> findByProgram(ProgramEntity programEntity);
}
