package CyPirates.avere.domain.beacon.repository;

import CyPirates.avere.domain.beacon.entity.BeaconEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconRepository extends JpaRepository<BeaconEntity, Long> {
    BeaconEntity findByBeaconUuid(String beaconUuid);
}
