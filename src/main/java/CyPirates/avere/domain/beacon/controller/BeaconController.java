package CyPirates.avere.domain.beacon.controller;

import CyPirates.avere.domain.beacon.dto.BeaconDto;
import CyPirates.avere.domain.beacon.service.BeaconService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j(topic = "BeaconController")
@RequiredArgsConstructor
@RequestMapping("/beacon")
public class BeaconController {
    private final BeaconService beaconService;
    @Operation(summary = "비콘 프로그램 조회하기", tags = {"비콘"})
    @GetMapping("/{beaconUuid}")
    public ResponseEntity<BeaconDto.Response> getBeaconProgram(@PathVariable String beaconUuid) {
        return ResponseEntity.ok(beaconService.getBeaconProgram(beaconUuid));
    }

    @Operation(summary = "비콘 프로그램 등록하기", tags = {"비콘"})
    @GetMapping("/register/{beaconUuid}")
    public ResponseEntity<BeaconDto.Response> registerBeaconProgram(@PathVariable String beaconUuid, @RequestParam("programId") Long programId) {
        return ResponseEntity.ok(beaconService.registerBeaconProgram(beaconUuid, programId));
    }

}
