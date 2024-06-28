package CyPirates.avere.domain.item.controller;

import CyPirates.avere.domain.item.dto.ItemDto;
import CyPirates.avere.domain.item.service.ItemService;
import CyPirates.avere.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j(topic = "ItemController")
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "아이템 등록하기", tags = {"아이템"})
    @PostMapping(value ="/register/{unitId}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto.Response> registerItem(@PathVariable Long unitId, @ModelAttribute ItemDto.Register request,
                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            return ResponseEntity.ok(itemService.registerItem(unitId, request,userDetails.getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "유닛 ID로 아이템 목록 조회하기", tags = {"아이템"})
    @GetMapping("/{unitId}")
    public ResponseEntity<List<ItemDto.Response>> getItemsByProgramId(@PathVariable Long unitId) {
        return ResponseEntity.ok(itemService.getItemsByProgramId(unitId));
    }

    @Operation(summary = "아이템 수정하기", tags = {"아이템"})
    @PutMapping(value = "/{itemId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDto.Response> updateItem(@PathVariable Long itemId, @ModelAttribute ItemDto.Register request,
                                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        try{
            return ResponseEntity.ok(itemService.updateItem(itemId, request,userDetails.getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "아이템 삭제하기", tags = {"아이템"})
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        itemService.deleteItem(itemId,userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "로그인 한 유저의 아이템 리스트 조회하기", tags = {"아이템"})
    @GetMapping("/my-items")
    public ResponseEntity<List<ItemDto.Response>> getItemsByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ItemDto.Response> items = itemService.getItemsByUser(userDetails.getUsername());
        return ResponseEntity.ok(items);
    }


}
