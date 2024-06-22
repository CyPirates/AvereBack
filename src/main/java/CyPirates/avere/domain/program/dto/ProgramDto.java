package CyPirates.avere.domain.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class ProgramDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "Program registeration dto", description = "프로그램 등록", type = "multipartForm")
    public static class Register {
        @NotBlank
        @Schema(name = "프로그램 이름", example = "2024 XX 박람회")
        private String programName;
        @Schema(name = "프로그램 설명", example = "프로그램 설명")
        private String programDescription;
//        @Schema(name = "프로그램 이미지", example = "프로그램 이미지")
//        private String programImage;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "Program Info Dto", description = "프로그램 정보")
    public static class Response {
        @NotNull
        @Schema(name = "프로그램 ID", example = "1")
        private Long programId;
        @NotBlank
        @Schema(name = "프로그램 이름", example = "2024 XX 박람회")
        private String programName;
        @Schema(name = "프로그램 설명", example = "프로그램 설명")
        private String programDescription;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "Program Update Dto", description = "프로그램 수정")
    public static class Update {
        @NotBlank
        @Schema(name = "프로그램 이름", example = "2024 XX 박람회")
        private String programName;
        @Schema(name = "프로그램 설명", example = "프로그램 설명")
        private String programDescription;
    }
}
