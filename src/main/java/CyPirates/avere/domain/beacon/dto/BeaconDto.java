package CyPirates.avere.domain.beacon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class BeaconDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "Beacon Info Dto", description = "비콘에 연결된 프로그램 정보")
    public static class Response {
        @Schema(name = "프로그램 ID", example = "1")
        private Long programId;
    }
}
