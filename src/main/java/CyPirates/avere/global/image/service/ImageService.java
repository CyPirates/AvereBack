package CyPirates.avere.global.image.service;

import CyPirates.avere.global.image.entity.ImageEntity;
import CyPirates.avere.global.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final String uploadDir = "uploads/";

    public ImageEntity storeImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
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

    public void deleteImage(Long id) {
        ImageEntity image = getImage(id);
        try {
            Files.deleteIfExists(Paths.get(image.getFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageRepository.delete(image);
    }
}
