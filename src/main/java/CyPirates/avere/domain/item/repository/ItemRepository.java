package CyPirates.avere.domain.item.repository;

import CyPirates.avere.domain.item.entity.ItemEntity;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByProgram(ProgramEntity program);
}
