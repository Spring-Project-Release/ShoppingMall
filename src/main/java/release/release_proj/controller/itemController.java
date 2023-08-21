package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.Item;
import release.release_proj.service.ItemService;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class itemController {

    private final ItemService itemService;

    @GetMapping
    //@ResponseBody
    public ResponseEntity<List<Item>> itemList() {
        List<Item> items = itemService.readItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable(name = "id") Long id) {
        Optional<Item> item = itemService.findOne(id);

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

    @PostMapping
    public ResponseEntity<String> join(@RequestBody Item item) {
        itemService.saveItem(item);
        return ResponseEntity.ok("Item created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Item item) {
        ///id를 따로 인자로 받아냐 하나 id와 item 객체가 같은지 확인해야 하는데
        /// id 없을 경우 오류처리해야함!!
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