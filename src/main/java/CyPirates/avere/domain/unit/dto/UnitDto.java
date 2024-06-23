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
        @Schema(example = "2024-01-01")
        private String date;
        @Schema(example = "1")
        private int count;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationResponse {
        @NotNull
        @Schema(example = "1")
        private Long unitId;
        @NotNull
        @Schema(example = "1")
        private Long reservationId;
        @NotNull
        @Schema(example = "1")
        private Long userId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterUnitRequest {
        @NotNull
        @Schema(example = "1")
        private Long programId;
        @NotBlank
        @Schema(example = "XX 부스")
        private String unitName;
        @Schema(example = "유닛 설명")
        private String unitDescription;
        private VisitCountResponse visitCount;
        @NotBlank
        @Schema(example = "2024-01-01 00:00:00")
        private String startTime;
        @NotBlank
        @Schema(example = "2024-01-01 00:00:00")
        private String endTime;
        @Schema(example = "장소")
        private String location;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitInfoResponse {
        @NotNull
        @Schema(example = "1")
        private Long unitId;
        @NotBlank
        @Schema(example = "XX 부스")
        private String unitName;
        @Schema(example = "유닛 설명")
        private String unitDescription;
        @NotBlank
        @Schema(example = "2024-01-01 00:00:00")
        private String startTime;
        @NotBlank
        @Schema(example = "2024-01-01 00:00:00")
        private String endTime;
        @Schema(example = "장소")
        private String location;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HostManageUnitInfoResponse {
        @NotNull
        @Schema(example = "1")
        private Long unitId;
        private List<VisitCountResponse> visitCount;
        @NotNull
        private List<ReservationResponse> reservations;
        @Schema(example = "1")
        private int reservationCount;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationInfoResponse {
        @NotNull
        @Schema(example = "1")
        private Long unitId;
        @NotNull
        @Schema(example = "1")
        private Long reservationId;
        @NotNull
        @Schema(example = "1")
        private Long userId;
        @NotNull
        @Schema(example = "1")
        private int waitingNumber;
    }
}
