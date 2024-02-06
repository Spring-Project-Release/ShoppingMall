package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.dto.ItemRequestDTO;
import release.release_proj.dto.ItemResponseDTO;
import release.release_proj.service.ItemService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> itemList() {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.readItems();
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ItemResponseDTO>> getItemByCategory(@PathVariable(name="category") String category) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.findByCategory(category);
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/isSoldout/{isSoldout}")
    public ResponseEntity<List<ItemResponseDTO>> getItemByIsSoldout(@PathVariable(name="isSoldout") Boolean isSoldout) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.findByIsSoldout(isSoldout);
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{itemId}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable(name = "itemId") Long itemId) {
        try {
            ItemResponseDTO itemDTO = itemService.findOne(itemId);
            return ResponseEntity.ok(itemDTO);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable(name = "name") String name) {
        try {
            ItemResponseDTO itemDTO= itemService.findByItemName(name);
            return ResponseEntity.ok(itemDTO);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sellerId/{sellerId}")
    public ResponseEntity<List<ItemResponseDTO>> getItemBySellerId(@PathVariable(name = "sellerId") String sellerId) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.findItemsBySellerId(sellerId);
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> newItem(@RequestBody ItemRequestDTO itemDTO) {
        try {
            int result = itemService.saveItem(itemDTO);

            if (result != 0) {
                return ResponseEntity.ok("Item created successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Item.");
            }
        } catch (IllegalStateException e) {
            // item 이름이 중복된 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            //외래키 제약을 위배한 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<String> update(@PathVariable(name = "itemId") Long itemId, @RequestBody ItemRequestDTO itemDTO) {
        itemDTO.setItemId(itemId); //item에 id를 set하지 않아도 url의 id를 가진 item을 update하도록 함
        int result = itemService.updateItem(itemDTO);
        if (result != 0){
            return ResponseEntity.ok("Item updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("존재하지 않는 itemId입니다.");
        }
    }

    @PutMapping("/{itemId}/isSoldout")
    public ResponseEntity<String> updateIsSoldout(@PathVariable(name = "itemId") Long itemId) {
        int result = itemService.updateIsSoldout(itemId);
        if (result != 0){
            return ResponseEntity.ok("Item isSoldout updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("존재하지 않는 itemId이거나 update 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable(name = "itemId") Long itemId) {
        int result = itemService.deleteItem(itemId);
        if (result != 0){
            return ResponseEntity.ok("Item deleted successfully.");
        }
        else { //해당 itemId가 존재하지 않아 삭제 동작이 필요하지 않았던 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 itemId입니다.");
        }
    }
}