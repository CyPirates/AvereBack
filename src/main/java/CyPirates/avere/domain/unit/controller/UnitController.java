package CyPirates.avere.domain.unit.controller;

import CyPirates.avere.domain.unit.dto.UnitDto;
import CyPirates.avere.domain.unit.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j(topic = "UnitController")
@RequiredArgsConstructor
@RequestMapping("/unit")
public class UnitController {
    private final UnitService unitService;

    // 게스트 - 유닛
    public ResponseEntity<UnitDto.UnitInfoResponse> getUnitInfo(Long unitId) {
        return ResponseEntity.ok(unitService.getUnitInfo(unitId));
    }


    // 호스트 - 유닛
    public ResponseEntity<UnitDto.HostManageUnitInfoResponse> getHostManageUnitInfo(Long unitId) {
        return ResponseEntity.ok(unitService.getHostManageUnitInfo(unitId));
    }

    public ResponseEntity<UnitDto.UnitInfoResponse> registerUnit(UnitDto.RegisterUnitRequest request) {
        return ResponseEntity.ok(unitService.registerUnit(request));
    }

    // 게스트 - 예약
    public ResponseEntity<UnitDto.ReservationInfoResponse> getReservationInfo(Long unitId, Long userId) {
        return ResponseEntity.ok(unitService.getReservationInfo(unitId, userId));
    }

    public ResponseEntity<UnitDto.ReservationResponse> registerReservation(Long unitId, Long userId) {
        return ResponseEntity.ok(unitService.registerReservation(unitId, userId));
    }

    public ResponseEntity<UnitDto.ReservationResponse> cancelReservation(Long reservationId) {
        return ResponseEntity.ok(unitService.cancelReservation(reservationId));
    }

    // 호스트 - 예약
    public ResponseEntity<UnitDto.HostManageUnitInfoResponse> approveReserver(Long reservationId) {
        return ResponseEntity.ok(unitService.approveReserver(reservationId));
    }

    public ResponseEntity<UnitDto.HostManageUnitInfoResponse> confirmVisit(Long reservationId) {
        return ResponseEntity.ok(unitService.confirmVisit(reservationId));
    }

    public ResponseEntity<UnitDto.HostManageUnitInfoResponse> rejectReserver(Long reservationId) {
        return ResponseEntity.ok(unitService.rejectReserver(reservationId));
    }
}
