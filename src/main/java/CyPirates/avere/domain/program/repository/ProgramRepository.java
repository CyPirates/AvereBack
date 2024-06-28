package CyPirates.avere.domain.program.repository;

import CyPirates.avere.domain.program.entity.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

    List<ProgramEntity> findByUserUsername(String username);
}
