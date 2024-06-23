package CyPirates.avere.domain.unit.service;

import CyPirates.avere.domain.unit.dto.UnitDto;
import CyPirates.avere.domain.unit.entity.ReservationEntity;
import CyPirates.avere.domain.unit.entity.UnitEntity;
import CyPirates.avere.domain.unit.repository.DailyVisitRepository;
import CyPirates.avere.domain.unit.repository.ReservationRepository;
import CyPirates.avere.domain.unit.repository.UnitRepository;
import CyPirates.avere.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "UnitService")
@Transactional
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;
    private final ReservationRepository reservationRepository;
    private final DailyVisitRepository dailyVisitRepository;
    private final UserRepository userRepository;

    public UnitDto.UnitInfoResponse getUnitInfo(Long unitId) {
        UnitEntity unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유닛입니다."));

        return UnitDto.UnitInfoResponse.builder()
                .unitId(unit.getId())
                .unitName(unit.getName())
                .unitDescription(unit.getDescription())
                .startTime(unit.getStartTime())
                .endTime(unit.getEndTime())
                .location(unit.getLocation())
                .build();
    }

    public UnitDto.UnitInfoResponse registerUnit(UnitDto.RegisterUnitRequest request) {
        UnitEntity unit = UnitEntity.builder()
                .name(request.getUnitName())
                .description(request.getUnitDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .build();

        unitRepository.save(unit);

        return UnitDto.UnitInfoResponse.builder()
                .unitId(unit.getId())
                .unitName(unit.getName())
                .unitDescription(unit.getDescription())
                .startTime(unit.getStartTime())
                .endTime(unit.getEndTime())
                .location(unit.getLocation())
                .build();
    }

    public UnitDto.HostManageUnitInfoResponse getHostManageUnitInfo(Long unitId) {
        UnitEntity unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유닛입니다."));

        List<ReservationEntity> reservations = reservationRepository.findAllByUnit(unit);


        return UnitDto.HostManageUnitInfoResponse.builder()
                .unitId(unit.getId())
                .reservations(reservations.stream()
                        .map(reservation -> UnitDto.ReservationResponse.builder()
                                .reservationId(reservation.getId())
                                .userId(reservation.getGuest().getId())
                                .build())
                        .collect(Collectors.toList()))
                .reservationCount(reservations.size())
                // todo: 일일 방문수
                .build();
    }

    public UnitDto.ReservationInfoResponse getReservationInfo(Long unitId, Long userId) {
        UnitEntity unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유닛입니다."));

        ReservationEntity reservations = reservationRepository.findByUnitAndGuest_Id(unit, userId);

        return UnitDto.ReservationInfoResponse.builder()
                .unitId(unit.getId())
                .userId(userId)
                .reservationId(reservations.getId())
                .build();
    }

    public UnitDto.ReservationResponse registerReservation(Long unitId, Long userId) {
        UnitEntity unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유닛입니다."));

        ReservationEntity reservation = ReservationEntity.builder()
                .unit(unit)
                .guest(userRepository.findById(userId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")))
                .status(ReservationEntity.Status.WAITING)
                .build();

        reservationRepository.save(reservation);

        return UnitDto.ReservationResponse.builder()
                .reservationId(reservation.getId())
                .userId(userId)
                .unitId(unitId)
                .build();
    }

    public UnitDto.ReservationResponse cancelReservation(Long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));

        reservationRepository.delete(reservation);

        return UnitDto.ReservationResponse.builder()
                .reservationId(reservation.getId())
                .userId(reservation.getGuest().getId())
                .unitId(reservation.getUnit().getId())
                .build();
    }

    public UnitDto.HostManageUnitInfoResponse approveReserver(Long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));

        return UnitDto.HostManageUnitInfoResponse.builder()
                .unitId(reservation.getUnit().getId())
                .reservations(List.of(UnitDto.ReservationResponse.builder()
                        .reservationId(reservation.getId())
                        .userId(reservation.getGuest().getId())
                        .build()))
                .reservationCount(1)
                .build();
    }

    public UnitDto.HostManageUnitInfoResponse confirmVisit(Long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));

        reservation.setStatus(ReservationEntity.Status.ACCEPTED);
        reservationRepository.save(reservation);

        return UnitDto.HostManageUnitInfoResponse.builder()
                .unitId(reservation.getUnit().getId())
                .reservations(List.of(UnitDto.ReservationResponse.builder()
                        .reservationId(reservation.getId())
                        .userId(reservation.getGuest().getId())
                        .build()))
                .reservationCount(1)
                .build();
    }

    public UnitDto.HostManageUnitInfoResponse rejectReserver(Long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));

        reservation.setStatus(ReservationEntity.Status.REJECTED);
        reservationRepository.save(reservation);

        return UnitDto.HostManageUnitInfoResponse.builder()
                .unitId(reservation.getUnit().getId())
                .reservations(List.of(UnitDto.ReservationResponse.builder()
                        .reservationId(reservation.getId())
                        .userId(reservation.getGuest().getId())
                        .build()))
                .reservationCount(1)
                .build();
    }
}
