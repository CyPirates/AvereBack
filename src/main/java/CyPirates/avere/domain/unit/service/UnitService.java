package CyPirates.avere.domain.unit.service;

import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.program.repository.ProgramRepository;
import CyPirates.avere.domain.unit.dto.UnitDto;
import CyPirates.avere.domain.unit.entity.ReservationEntity;
import CyPirates.avere.domain.unit.entity.UnitEntity;
import CyPirates.avere.domain.unit.repository.DailyVisitRepository;
import CyPirates.avere.domain.unit.repository.ReservationRepository;
import CyPirates.avere.domain.unit.repository.UnitRepository;
import CyPirates.avere.domain.user.entity.User;
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
    private final ProgramRepository programRepository;

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

    public UnitDto.UnitInfoResponse registerUnit(UnitDto.RegisterUnitRequest request, String username) {
        ProgramEntity program = programRepository.findById(request.getProgramId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 프로그램입니다."));

        UnitEntity unit = UnitEntity.builder()
                .name(request.getUnitName())
                .description(request.getUnitDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .program(program)
                .host(userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")))
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

    public UnitDto.HostManageUnitInfoResponse getHostManageUnitInfo(Long unitId, String username) {
        UnitEntity unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유닛입니다."));

        if (!unit.getHost().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 유닛의 호스트만 유닛 관리 정보를 조회할 수 있습니다.");
        }

        List<ReservationEntity> reservations = reservationRepository.findAllByUnit(unit);

        return UnitDto.HostManageUnitInfoResponse.builder()
                .unitId(unit.getId())
                .reservations(reservations.stream()
                        .map(reservation -> UnitDto.ReservationResponse.builder()
                                .unitId(unit.getId())
                                .reservationId(reservation.getId())
                                .userId(reservation.getGuest().getId())
                                .build())
                        .collect(Collectors.toList()))
                .reservationCount(reservations.size())
                // todo: 일일 방문수
                .build();
    }

    public UnitDto.ReservationInfoResponse getReservationInfo(Long unitId, String username) {
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        UnitEntity unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유닛입니다."));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));

        ReservationEntity reservations = reservationRepository.findByUnitAndGuest(unit, user);
        int waitingNumber = reservationRepository.countByUnitAndStatusAndIdLessThan(unit, ReservationEntity.Status.WAITING, reservations.getId());

        return UnitDto.ReservationInfoResponse.builder()
                .unitId(unit.getId())
                .userId(user.getId())
                .reservationId(reservations.getId())
                .waitingNumber(waitingNumber)
                .build();
    }

    public UnitDto.ReservationInfoResponse registerReservation(Long unitId, String username) {
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        UnitEntity unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유닛입니다."));

        ReservationEntity reservation = ReservationEntity.builder()
                .unit(unit)
                .guest(userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")))
                .status(ReservationEntity.Status.WAITING)
                .build();

        reservationRepository.save(reservation);

        int waitingNumber = reservationRepository.countByUnitAndStatusAndIdLessThan(unit, ReservationEntity.Status.WAITING, reservation.getId());

        return UnitDto.ReservationInfoResponse.builder()
                .unitId(unit.getId())
                .userId(reservation.getGuest().getId())
                .reservationId(reservation.getId())
                .waitingNumber(waitingNumber)
                .build();
    }

    public UnitDto.ReservationResponse cancelReservation(Long reservationId, String username) {
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        ReservationEntity reservation = reservationRepository.findByIdAndGuestUsername(reservationId, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));

        reservationRepository.delete(reservation);

        return UnitDto.ReservationResponse.builder()
                .reservationId(reservation.getId())
                .userId(reservation.getGuest().getId())
                .unitId(reservation.getUnit().getId())
                .build();
    }

    public UnitDto.HostManageUnitInfoResponse approveReserver(Long reservationId, String username) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));

        if (!reservation.getUnit().getHost().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 유닛의 호스트만 예약을 승인할 수 있습니다.");
        }

        int reservationCount = reservationRepository.countByUnitAndStatus(reservation.getUnit(), ReservationEntity.Status.WAITING);

        return UnitDto.HostManageUnitInfoResponse.builder()
                .unitId(reservation.getUnit().getId())
                .reservations(List.of(UnitDto.ReservationResponse.builder()
                        .unitId(reservation.getUnit().getId())
                        .reservationId(reservation.getId())
                        .userId(reservation.getGuest().getId())
                        .build()))
                .reservationCount(reservationCount)
                .build();
    }

    public UnitDto.HostManageUnitInfoResponse confirmVisit(Long reservationId, String username) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));

        if (!reservation.getUnit().getHost().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 유닛의 호스트만 예약을 확정할 수 있습니다.");
        }

        reservation.setStatus(ReservationEntity.Status.ACCEPTED);
        reservationRepository.save(reservation);

        int reservationCount = reservationRepository.countByUnitAndStatus(reservation.getUnit(), ReservationEntity.Status.WAITING);

        return UnitDto.HostManageUnitInfoResponse.builder()
                .unitId(reservation.getUnit().getId())
                .reservations(List.of(UnitDto.ReservationResponse.builder()
                        .unitId(reservation.getUnit().getId())
                        .reservationId(reservation.getId())
                        .userId(reservation.getGuest().getId())
                        .build()))
                .reservationCount(reservationCount)
                .build();
    }

    public UnitDto.HostManageUnitInfoResponse rejectReserver(Long reservationId, String username) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."));

        if (!reservation.getUnit().getHost().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 유닛의 호스트만 예약을 거부할 수 있습니다.");
        }

        reservation.setStatus(ReservationEntity.Status.REJECTED);
        reservationRepository.save(reservation);

        int reservationCount = reservationRepository.countByUnitAndStatus(reservation.getUnit(), ReservationEntity.Status.WAITING);

        return UnitDto.HostManageUnitInfoResponse.builder()
                .unitId(reservation.getUnit().getId())
                .reservations(List.of(UnitDto.ReservationResponse.builder()
                        .unitId(reservation.getUnit().getId())
                        .reservationId(reservation.getId())
                        .userId(reservation.getGuest().getId())
                        .build()))
                .reservationCount(reservationCount)
                .build();
    }

    public List<UnitDto.ReservationInfoResponse> getMyReservationInfo(String username) {
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));

        List<ReservationEntity> reservation = reservationRepository.findAllByGuest(user);

        return reservation.stream()
                .map(reservationEntity -> UnitDto.ReservationInfoResponse.builder()
                        .unitId(reservationEntity.getUnit().getId())
                        .userId(reservationEntity.getGuest().getId())
                        .reservationId(reservationEntity.getId())
                        .waitingNumber(reservationRepository.countByUnitAndStatusAndIdLessThan(reservationEntity.getUnit(), ReservationEntity.Status.WAITING, reservationEntity.getId()))
                        .build())
                .collect(Collectors.toList());
    }
}
