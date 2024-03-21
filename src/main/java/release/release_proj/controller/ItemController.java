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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> itemList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.readItems(page, size);
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/createdAt")
    public ResponseEntity<List<ItemResponseDTO>> itemListByCreatedAt(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.readOrderByCreatedAtDesc(page, size);
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<List<ItemResponseDTO>> itemListByCount(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.readOrderByCountDesc(page, size);
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<ItemResponseDTO>> getItemByCategory(@PathVariable(name="category") String category, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.findByCategory(category, page, size);
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/isSoldout/{isSoldout}")
    public ResponseEntity<List<ItemResponseDTO>> getItemByIsSoldout(@PathVariable(name="isSoldout") Boolean isSoldout, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.findByIsSoldout(isSoldout, page, size);
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
    public ResponseEntity<List<ItemResponseDTO>> getItemBySellerId(@PathVariable(name = "sellerId") String sellerId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<ItemResponseDTO> itemDTOs = itemService.findItemsBySellerId(sellerId, page, size);
            return ResponseEntity.ok(itemDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> newItem(@RequestBody ItemRequestDTO itemDTO) {
        try {
            itemService.saveItem(itemDTO);
            return ResponseEntity.ok("Item created successfully.");
        } catch (IllegalStateException e) {
            // item 이름이 중복된 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // 외래키 제약을 위배한 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // 그 외의 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Item.");
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<String> update(@PathVariable(name = "itemId") Long itemId, @RequestBody ItemRequestDTO itemDTO) {
        itemDTO.setItemId(itemId); // item에 id를 set하지 않아도 url의 id를 가진 item을 update하도록 함
        try {
            itemService.updateItem(itemDTO);
            return ResponseEntity.ok("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
        try {
            itemService.deleteItem(itemId);
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}