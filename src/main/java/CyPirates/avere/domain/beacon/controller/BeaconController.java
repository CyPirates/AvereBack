package CyPirates.avere.domain.beacon.controller;

import CyPirates.avere.domain.beacon.dto.BeaconDto;
import CyPirates.avere.domain.beacon.service.BeaconService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j(topic = "BeaconController")
@RequiredArgsConstructor
@RequestMapping("/beacon")
public class BeaconController {
    private final BeaconService beaconService;
    @Operation(summary = "비콘 프로그램 조회하기", tags = {"비콘"})
    @GetMapping("/{beaconId}")
    public ResponseEntity<BeaconDto.Response> getBeaconProgram(@PathVariable String beaconId) {
        return ResponseEntity.ok(beaconService.getBeaconProgram(beaconId));
    }
}
