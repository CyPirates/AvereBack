package CyPirates.avere.domain.unit.controller;

import CyPirates.avere.domain.item.dto.ItemDto;
import CyPirates.avere.domain.program.dto.ProgramDto;
import CyPirates.avere.domain.unit.dto.UnitDto;
import CyPirates.avere.domain.unit.service.UnitService;
import CyPirates.avere.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j(topic = "UnitController")
@RequiredArgsConstructor
@RequestMapping("/unit")
public class UnitController {
    private final UnitService unitService;

    @Operation(summary = "로그인한 유저가 등록한 유닛 조회하기", tags = {"유닛"})
    @GetMapping("/my-units")
    public ResponseEntity<List<UnitDto.UnitInfoResponse>> getUnitsByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<UnitDto.UnitInfoResponse> units = unitService.getUnitsByUser(userDetails.getUsername());
        return ResponseEntity.ok(units);
    }
    @Operation(summary = "프로그램 ID로 유닛 목록 조회하기", tags = {"유닛"})
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<UnitDto.UnitInfoResponse>> getUnitsByProgramId(@PathVariable Long programId) {
        return ResponseEntity.ok(unitService.getUnitsByProgramId(programId));
    }
    // 게스트 - 유닛
    @Operation(summary = "유닛 기본정보 조회하기", tags = {"유닛"})
    @GetMapping("/{unitId}")
    public ResponseEntity<UnitDto.UnitInfoResponse> getUnitInfo(@PathVariable Long unitId) {
        return ResponseEntity.ok(unitService.getUnitInfo(unitId));
    }


    // 호스트 - 유닛
    @Operation(summary = "유닛 관리정보 조회하기", tags = {"유닛"})
    @GetMapping("/host/{unitId}")
    public ResponseEntity<UnitDto.HostManageUnitInfoResponse> getHostManageUnitInfo(@PathVariable Long unitId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.getHostManageUnitInfo(unitId, userDetails.getUsername()));
    }

    @Operation(summary = "유닛 등록하기", tags = {"유닛"})
    @PostMapping("/register")
    public ResponseEntity<UnitDto.UnitInfoResponse> registerUnit(@RequestBody UnitDto.RegisterUnitRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.registerUnit(request, userDetails.getUsername()));
    }

    // 게스트 - 예약
    @Operation(summary = "예약정보 조회하기", tags = {"유닛"})
    @GetMapping("/{unitId}/reservation")
    public ResponseEntity<UnitDto.ReservationInfoResponse> getReservationInfo(@PathVariable Long unitId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.getReservationInfo(unitId, userDetails.getUsername()));
    }

    @Operation(summary = "내 예약정보 조회하기", tags = {"유닛"})
    @GetMapping("/reservation")
    public ResponseEntity<List<UnitDto.ReservationInfoResponse>> getMyReservationInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.getMyReservationInfo(userDetails.getUsername()));
    }

    @Operation(summary = "예약하기", tags = {"유닛"})
    @PostMapping("/{unitId}/reservation")
    public ResponseEntity<UnitDto.ReservationInfoResponse> registerReservation(@PathVariable Long unitId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.registerReservation(unitId, userDetails.getUsername()));
    }

    @Operation(summary = "예약 취소하기", tags = {"유닛"})
    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<UnitDto.ReservationResponse> cancelReservation(Long reservationId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.cancelReservation(reservationId, userDetails.getUsername()));
    }

    // 호스트 - 예약
    @Operation(summary = "예약 승인하기", tags = {"유닛"})
    @PutMapping("/reservation/{reservationId}/approve")
    public ResponseEntity<UnitDto.HostManageUnitInfoResponse> approveReserver(Long reservationId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.approveReserver(reservationId, userDetails.getUsername()));
    }

    @Operation(summary = "방문 확인하기", tags = {"유닛"})
    @PutMapping("/reservation/{reservationId}/visit")
    public ResponseEntity<UnitDto.HostManageUnitInfoResponse> confirmVisit(Long reservationId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.confirmVisit(reservationId, userDetails.getUsername()));
    }

    @Operation(summary = "예약 거절하기", tags = {"유닛"})
    @PutMapping("/reservation/{reservationId}/reject")
    public ResponseEntity<UnitDto.HostManageUnitInfoResponse> rejectReserver(Long reservationId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(unitService.rejectReserver(reservationId, userDetails.getUsername()));
    }
}
