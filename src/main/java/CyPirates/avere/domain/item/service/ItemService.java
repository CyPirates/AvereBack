package CyPirates.avere.domain.item.service;

import CyPirates.avere.domain.item.dto.ItemDto;
import CyPirates.avere.domain.item.entity.ItemEntity;
import CyPirates.avere.domain.item.repository.ItemRepository;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.program.repository.ProgramRepository;
import CyPirates.avere.domain.unit.entity.UnitEntity;
import CyPirates.avere.domain.unit.repository.UnitRepository;
import CyPirates.avere.domain.user.repository.UserRepository;
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
    private final UnitRepository unitRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;

    public ItemDto.Response registerItem(Long unitId, ItemDto.Register request,String username) throws IOException {
        UnitEntity unitEntity = unitRepository.findById(unitId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유닛이 존재하지 않습니다."));


        ImageEntity imageEntity = imageService.storeImage(request.getImage());

        ItemEntity itemEntity = ItemEntity.builder()
                .itemName(request.getItemName())
                .itemDescription(request.getItemDescription())
                .itemLocation(request.getItemLocation())
                .itemTime(request.getItemTime())
                .unit(unitEntity)
                .user(userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")))
                .image(imageEntity)
                .build();

        ItemEntity savedItemEntity = itemRepository.save(itemEntity);

        return ItemDto.Response.builder()
                .itemId(savedItemEntity.getId())
                .itemName(savedItemEntity.getItemName())
                .itemDescription(savedItemEntity.getItemDescription())
                .itemLocation(savedItemEntity.getItemLocation())
                .itemTime(savedItemEntity.getItemTime())
                .imageUrl(imageService.getImageUrl(savedItemEntity.getImage().getId()))
                .userId(savedItemEntity.getUser().getId())
                .build();
    }

    public List<ItemDto.Response> getItemsByProgramId(Long programId) {
        UnitEntity unitEntity = unitRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유닛이 존재하지 않습니다."));

        List<ItemEntity> items = itemRepository.findByUnit(unitEntity);

        return items.stream()
                .map(item -> ItemDto.Response.builder()
                        .itemId(item.getId())
                        .itemName(item.getItemName())
                        .itemDescription(item.getItemDescription())
                        .itemLocation(item.getItemLocation())
                        .itemTime(item.getItemTime())
                        .userId(item.getUser().getId())
                        .imageUrl(imageService.getImageUrl(item.getImage().getId()))
                        .build())
                .collect(Collectors.toList());
    }

    public ItemDto.Response updateItem(Long itemId, ItemDto.Register request,String username) throws IOException {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        if(!itemEntity.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("해당 아이템에 대한 권한이 없습니다.");
        }

        ImageEntity imageEntity = imageService.storeImage(request.getImage());

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
                .imageUrl(imageService.getImageUrl(updatedItemEntity.getImage().getId()))
                .userId(updatedItemEntity.getUser().getId())
                .build();
    }

    public void deleteItem(Long itemId,String username) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        if(!itemEntity.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("해당 아이템에 대한 권한이 없습니다.");
        }

        itemRepository.delete(itemEntity);
    }

    public List<ItemDto.Response> getItemsByUser(String username) {
        List<ItemEntity> items = itemRepository.findByUserUsername(username);

        return items.stream().map(itemEntity -> ItemDto.Response.builder()
                .itemId(itemEntity.getId())
                .itemName(itemEntity.getItemName())
                .itemDescription(itemEntity.getItemDescription())
                .itemLocation(itemEntity.getItemLocation())
                .itemTime(itemEntity.getItemTime())
                .imageUrl(imageService.getImageUrl(itemEntity.getImage().getId()))
                .userId(itemEntity.getUser().getId())
                .build()).collect(Collectors.toList());
    }
}
