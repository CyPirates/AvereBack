package CyPirates.avere.global.image.controller;

import CyPirates.avere.global.image.entity.ImageEntity;
import CyPirates.avere.global.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j(topic = "ImageController")
@RequiredArgsConstructor
@RequestMapping("/image")
@Tag(name = "Image", description = "이미지 API")
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 등록", description = "Uploads an image to the server")
    public ResponseEntity<ImageEntity> uploadImage(
            @Parameter(description = "File to upload", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            ImageEntity image = imageService.storeImage(file);
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "이미지 조회", description = "Get an image by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ImageEntity> getImage(@PathVariable Long id) {
        ImageEntity image = imageService.getImage(id);
        return ResponseEntity.ok(image);
    }

    @Operation(summary = "이미지 삭제", description = "Delete an image by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
