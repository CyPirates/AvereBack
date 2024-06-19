package CyPirates.avere.domain.beacon.service;

import CyPirates.avere.domain.beacon.dto.BeaconDto;
import CyPirates.avere.domain.beacon.entity.BeaconEntity;
import CyPirates.avere.domain.beacon.repository.BeaconRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "PlaylistService")
@Transactional
@RequiredArgsConstructor
public class BeaconService {
    private final BeaconRepository beaconRepository;

    /**
     * 비콘에 연결된 프로그램 정보를 조회한다.
     *
     * @param beaconId 비콘 ID
     * @return 비콘에 연결된 프로그램 정보 DTO
     */
    public BeaconDto.Response getBeaconProgram(String beaconId) {
        // beacon id 파싱
        String beaconUuid = parseBeaconId(beaconId);
        BeaconEntity beacon = beaconRepository.findByBeaconUuid(beaconUuid);
        return BeaconDto.Response.builder()
                .programId(beacon.getProgram().getId())
                .build();
    }

    private String parseBeaconId(String beaconId) {
        // remove colon and dash. capitalize all letters
        return beaconId.replaceAll("[-:]", "").toUpperCase();
    }
}
