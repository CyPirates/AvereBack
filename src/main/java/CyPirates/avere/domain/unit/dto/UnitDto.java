package CyPirates.avere.domain.unit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

public class UnitDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VisitCountResponse {
        @NotNull
        @Schema(name = "날짜", example = "2024-01-01")
        private String date;
        @Schema(name = "방문수", example = "1")
        private int count;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationResponse {
        @NotNull
        @Schema(name = "유닛 ID", example = "1")
        private Long unitId;
        @NotNull
        @Schema(name = "예약 ID", example = "1")
        private Long reservationId;
        @NotNull
        @Schema(name = "유저 ID", example = "1")
        private Long userId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterUnitRequest {
        @NotBlank
        @Schema(name = "유닛 이름", example = "XX 부스")
        private String unitName;
        @Schema(name = "유닛 설명", example = "유닛 설명")
        private String unitDescription;
        @Schema(name = "유닛 방문수")
        private VisitCountResponse visitCount;
        @NotBlank
        @Schema(name = "시작 시간", example = "2024-01-01 00:00:00")
        private String startTime;
        @NotBlank
        @Schema(name = "종료 시간", example = "2024-01-01 00:00:00")
        private String endTime;
        @Schema(name = "장소", example = "장소")
        private String location;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitInfoResponse {
        @NotNull
        @Schema(name = "유닛 ID", example = "1")
        private Long unitId;
        @NotBlank
        @Schema(name = "유닛 이름", example = "XX 부스")
        private String unitName;
        @Schema(name = "유닛 설명", example = "유닛 설명")
        private String unitDescription;
        @NotBlank
        @Schema(name = "시작 시간", example = "2024-01-01 00:00:00")
        private String startTime;
        @NotBlank
        @Schema(name = "종료 시간", example = "2024-01-01 00:00:00")
        private String endTime;
        @Schema(name = "장소", example = "장소")
        private String location;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HostManageUnitInfoResponse {
        @NotNull
        @Schema(name = "유닛 ID", example = "1")
        private Long unitId;
        @Schema(name = "유닛 방문수")
        private List<VisitCountResponse> visitCount;
        @NotNull
        @Schema(name = "유닛 예약 정보")
        private List<ReservationResponse> reservations;
        @Schema(name = "유닛 예약수", example = "1")
        private int reservationCount;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationInfoResponse {
        @NotNull
        @Schema(name = "유닛 ID", example = "1")
        private Long unitId;
        @NotNull
        @Schema(name = "예약 ID", example = "1")
        private Long reservationId;
        @NotNull
        @Schema(name = "유저 ID", example = "1")
        private Long userId;
        @NotNull
        @Schema(name = "대기 번호", example = "1")
        private int waitingNumber;
    }
}
