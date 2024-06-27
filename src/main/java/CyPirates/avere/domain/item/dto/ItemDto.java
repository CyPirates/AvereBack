package CyPirates.avere.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class ItemDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "item registeration", description = "아이템 등록", type = "multipartForm")
    public static class Register {
        @NotBlank
        @Schema(name = "itemName", example = "아이템 이름")
        private String itemName;
        @Schema(name = "itemDescription", example = "아이템 설명")
        private String itemDescription;
        @Schema(name = "itemLocation", example = "아이템 위치")
        private String itemLocation;
        @Schema(name = "itemTime", example = "아이템 시간")
        private String itemTime;
        @NotNull
        @Schema(name = "image", description = "플레이리스트 이미지입니다.", type = "string", format = "binary")
        private MultipartFile image;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "Item Info Dto", description = "아이템 정보")
    public static class Response{
        @NotNull
        @Schema(name = "itemId", example = "1")
        private Long itemId;
        @Schema(name = "itemName", example = "아이템 이름")
        private String itemName;
        @Schema(name = "itemDescription", example = "아이템 설명")
        private String itemDescription;
        @Schema(name = "itemLocation", example = "아이템 위치")
        private String itemLocation;
        @Schema(name = "itemTime", example = "아이템 시간")
        private String itemTime;
        @Schema(name = "imageUrl", example = "http://localhost:8080/images/1.jpg")
        private String imageUrl;
    }

}
