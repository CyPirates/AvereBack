package CyPirates.avere.domain.item.service;

import CyPirates.avere.domain.item.dto.ItemDto;
import CyPirates.avere.domain.item.entity.ItemEntity;
import CyPirates.avere.domain.item.repository.ItemRepository;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.program.repository.ProgramRepository;
import CyPirates.avere.global.image.entity.ImageEntity;
import CyPirates.avere.global.image.repository.ImageRepository;
import CyPirates.avere.global.image.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "ItemService")
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProgramRepository programRepository;
    private final ImageService imageService;

    public ItemDto.Response registerItem(Long programId, ItemDto.Register request, MultipartFile file) throws IOException {
        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다."));

        ImageEntity imageEntity = null;
        if (file != null && !file.isEmpty()){
            imageEntity = imageService.storeImage(file);
        }

        ItemEntity itemEntity = ItemEntity.builder()
                .itemName(request.getItemName())
                .itemDescription(request.getItemDescription())
                .itemLocation(request.getItemLocation())
                .itemTime(request.getItemTime())
                .program(programEntity)
                .image(imageEntity)
                .build();

        ItemEntity savedItemEntity = itemRepository.save(itemEntity);

        return ItemDto.Response.builder()
                .itemId(savedItemEntity.getId())
                .itemName(savedItemEntity.getItemName())
                .itemDescription(savedItemEntity.getItemDescription())
                .itemLocation(savedItemEntity.getItemLocation())
                .itemTime(savedItemEntity.getItemTime())
                .imageId(savedItemEntity.getImage() != null ? savedItemEntity.getImage().getId() : null)
                .build();
    }

    public List<ItemDto.Response> getItemsByProgramId(Long programId) {
        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다."));

        List<ItemEntity> items = itemRepository.findByProgram(programEntity);

        return items.stream()
                .map(item -> ItemDto.Response.builder()
                        .itemId(item.getId())
                        .itemName(item.getItemName())
                        .itemDescription(item.getItemDescription())
                        .itemLocation(item.getItemLocation())
                        .itemTime(item.getItemTime())
                        .imageId(item.getImage() != null ? item.getImage().getId() : null)
                        .build())
                .collect(Collectors.toList());
    }

    public ItemDto.Response updateItem(Long itemId, ItemDto.Register request ,MultipartFile file) throws IOException {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        ImageEntity imageEntity = null;
        if (file != null && !file.isEmpty()){
            imageEntity = imageService.storeImage(file);
        }


        itemEntity.setItemName(request.getItemName());
        itemEntity.setItemDescription(request.getItemDescription());
        itemEntity.setItemLocation(request.getItemLocation());
        itemEntity.setItemTime(request.getItemTime());
        itemEntity.setImage(imageEntity);

        ItemEntity updatedItemEntity = itemRepository.save(itemEntity);

        return ItemDto.Response.builder()
                .itemId(updatedItemEntity.getId())
                .itemName(updatedItemEntity.getItemName())
                .itemDescription(updatedItemEntity.getItemDescription())
                .itemLocation(updatedItemEntity.getItemLocation())
                .itemTime(updatedItemEntity.getItemTime())
                .imageId(updatedItemEntity.getImage() != null ? updatedItemEntity.getImage().getId() : null)
                .build();
    }

    public void deleteItem(Long itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        itemRepository.delete(itemEntity);
    }
}
