package CyPirates.avere.domain.item.service;

import CyPirates.avere.domain.item.dto.ItemDto;
import CyPirates.avere.domain.item.entity.ItemEntity;
import CyPirates.avere.domain.item.repository.ItemRepository;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.program.repository.ProgramRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "ItemService")
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProgramRepository programRepository;

    public ItemDto.Response registerItem(Long programId, ItemDto.Register request) {
        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다."));

        ItemEntity itemEntity = ItemEntity.builder()
                .itemName(request.getItemName())
                .itemDescription(request.getItemDescription())
                .itemLocation(request.getItemLocation())
                .itemTime(request.getItemTime())
                .program(programEntity)
                .build();

        ItemEntity savedItemEntity = itemRepository.save(itemEntity);

        return ItemDto.Response.builder()
                .itemId(savedItemEntity.getId())
                .itemName(savedItemEntity.getItemName())
                .itemDescription(savedItemEntity.getItemDescription())
                .itemLocation(savedItemEntity.getItemLocation())
                .itemTime(savedItemEntity.getItemTime())
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
                        .build())
                .collect(Collectors.toList());
    }

    public ItemDto.Response updateItem(Long itemId, ItemDto.Register request) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        itemEntity.setItemName(request.getItemName());
        itemEntity.setItemDescription(request.getItemDescription());
        itemEntity.setItemLocation(request.getItemLocation());
        itemEntity.setItemTime(request.getItemTime());

        ItemEntity updatedItemEntity = itemRepository.save(itemEntity);

        return ItemDto.Response.builder()
                .itemId(updatedItemEntity.getId())
                .itemName(updatedItemEntity.getItemName())
                .itemDescription(updatedItemEntity.getItemDescription())
                .itemLocation(updatedItemEntity.getItemLocation())
                .itemTime(updatedItemEntity.getItemTime())
                .build();
    }

    public void deleteItem(Long itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

        itemRepository.delete(itemEntity);
    }
}
