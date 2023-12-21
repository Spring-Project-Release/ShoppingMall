package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.Item;
import release.release_proj.service.ItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> itemList() {
        Optional<List<Item>> items = itemService.readItems();

        if (items.isPresent()){
            return ResponseEntity.ok(items.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Item>> itemCategory(@PathVariable(name="category") String category) {
        Optional<List<Item>> items = itemService.findByCategory(category);

        if (items.isPresent()){
            return ResponseEntity.ok(items.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/isSoldout/{isSoldout}")
    public ResponseEntity<List<Item>> itemIsSoldout(@PathVariable(name="isSoldout") Boolean isSoldout) {
        Optional<List<Item>> items = itemService.findByIsSoldout(isSoldout);

        if (items.isPresent()){
            return ResponseEntity.ok(items.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable(name = "itemId") Long itemId) {
        Optional<Item> item = itemService.findOne(itemId);

        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
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