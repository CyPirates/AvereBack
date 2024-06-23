package CyPirates.avere.global.image.repository;

import CyPirates.avere.global.image.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long>{
}
