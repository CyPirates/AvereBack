package CyPirates.avere.domain.beacon.service;

import CyPirates.avere.domain.beacon.dto.BeaconDto;
import CyPirates.avere.domain.beacon.entity.BeaconEntity;
import CyPirates.avere.domain.beacon.repository.BeaconRepository;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.program.repository.ProgramRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j(topic = "BeaconService")
@Transactional
@RequiredArgsConstructor
public class BeaconService {
    private final BeaconRepository beaconRepository;
    private final ProgramRepository programRepository;

    /**
     * 비콘에 연결된 프로그램 정보를 조회한다.
     *
     * @param beaconUuid 비콘 ID
     * @return 비콘에 연결된 프로그램 정보 DTO
     */
    public BeaconDto.Response getBeaconProgram(String beaconUuid) {
        BeaconEntity beacon = beaconRepository.findByBeaconUuid(parseBeaconId(beaconUuid)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 비콘이 존재하지 않습니다."));

        return BeaconDto.Response.builder()
                .beaconId(beacon.getBeaconUuid())
                .programId(beacon.getProgram().getId())
                .build();
    }


    /**
     * 비콘에 프로그램을 등록한다.
     * @param beaconUuid
     * @param programId
     * @return
     */
    public BeaconDto.Response registerBeaconProgram(String beaconUuid, Long programId) {
        ProgramEntity program = programRepository.findById(programId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 프로그램이 존재하지 않습니다."));
        BeaconEntity beacon = BeaconEntity.builder()
                .beaconUuid(parseBeaconId(beaconUuid))
                .program(program)
                .build();
        beaconRepository.save(beacon);
        return BeaconDto.Response.builder()
                .beaconId(beacon.getBeaconUuid())
                .programId(beacon.getProgram().getId())
                .build();
    }

    private String parseBeaconId(String beaconId) {
        // remove colon and dash. capitalize all letters
        return beaconId.replaceAll("[-:]", "").toUpperCase();
    }
}
