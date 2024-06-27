package CyPirates.avere.global.image.service;

import CyPirates.avere.global.image.entity.ImageEntity;
import CyPirates.avere.global.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final String uploadDir = "uploads/";

    public ImageEntity storeImage(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + (fileExtension.isEmpty() ? "" : "." + fileExtension);
        Path filePath = Paths.get(uploadDir + fileName);

        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath);

        ImageEntity image = ImageEntity.builder()
                .fileName(fileName)
                .fileType(file.getContentType())
                .filePath(filePath.toString())
                .fileSize(file.getSize())
                .build();

        return imageRepository.save(image);
    }

    public ImageEntity getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
    }
    public String getImageUrl(Long id) {
        ImageEntity image = getImage(id);
        String scheme = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getScheme();
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().scheme(scheme).build().toUriString();
        return baseUrl + "/images/" + image.getFileName();
    }

    public String getUploadDir() {
        return uploadDir;
    }
    public void deleteImage(Long id) {
        ImageEntity image = getImage(id);
        try {
            Files.deleteIfExists(Paths.get(image.getFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageRepository.delete(image);
    }
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

}
