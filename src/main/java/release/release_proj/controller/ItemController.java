package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.Item;
import release.release_proj.service.ItemService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> itemList() {
        try {
            List<Item> items = itemService.readItems();
            return ResponseEntity.ok(items);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Item>> getItemByCategory(@PathVariable(name="category") String category) {
        try {
            List<Item> items = itemService.findByCategory(category);
            return ResponseEntity.ok(items);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/isSoldout/{isSoldout}")
    public ResponseEntity<List<Item>> getItemByIsSoldout(@PathVariable(name="isSoldout") Boolean isSoldout) {
        try {
            List<Item> items = itemService.findByIsSoldout(isSoldout);
            return ResponseEntity.ok(items);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable(name = "itemId") Long itemId) {
        try {
            Item item = itemService.findOne(itemId);
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Item> getItem(@PathVariable(name = "name") String name) {
        try {
            Item item = itemService.findByItemName(name);
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sellerId/{sellerId}")
    public ResponseEntity<List<Item>> getItemBySellerId(@PathVariable(name = "sellerId") String sellerId) {
        try {
            List<Item> items = itemService.findItemsBySellerId(sellerId);
            return ResponseEntity.ok(items);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> newItem(@RequestBody Item item) {
        try {
            int result = itemService.saveItem(item);

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
    public ResponseEntity<String> update(@PathVariable(name = "itemId") Long itemId, @RequestBody Item item) {
        item.setItemId(itemId); //item에 id를 set하지 않아도 url의 id를 가진 item을 update하도록 함
        int result = itemService.updateItem(item);
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

//item의 id가 null이면 안됨 => @Valid를 contorller에, @NotNull을 dto에 넣어주기!!!