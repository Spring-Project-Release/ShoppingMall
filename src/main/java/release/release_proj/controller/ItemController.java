package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.Item;
import release.release_proj.service.ItemService;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    //@ResponseBody
    public ResponseEntity<List<Item>> itemList() {
        List<Item> items = itemService.readItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable(name = "itemId") Long itemId) {
        Optional<Item> item = itemService.findOne(itemId);

        /*
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
         */

        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
             return ResponseEntity.notFound().build();
        }
    }

    //item의 id가 null일 수 있음 => controller에 @NotNull 넣으면 좋을 듯! @Valid를 contorller에, @NotNull을 dto에 넣어주기!!!
    @PostMapping
    public ResponseEntity<String> join(@RequestBody Item item) {
        Long saveId = itemService.saveItem(item);
        return ResponseEntity.ok("Item created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody Item item) {
        //Item existingItem = itemService.findOne(id).get();
        /*if (item.getId().equals(id)) {
            itemService.updateItem(item);
            return ResponseEntity.ok("Item updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid request. The provided ID does not match the item's ID.");
        }*/
        item.setItemId(id); //item에 id를 넣지 않고 update해도 url의 id를 가진 item을 update하도록 함
        itemService.updateItem(item);
        return ResponseEntity.ok("Item updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        /// id 없을 경우 오류처리해야함!!
        itemService.deleteItem(id);
        return ResponseEntity.ok("Item deleted successfully.");
    }
}