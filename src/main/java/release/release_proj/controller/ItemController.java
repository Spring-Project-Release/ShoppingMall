/*package release.release_proj.controller;

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


        //return item.map(ResponseEntity::ok)
        //        .orElse(ResponseEntity.notFound().build());

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
        //if (item.getId().equals(id)) {
        //    itemService.updateItem(item);
        //    return ResponseEntity.ok("Item updated successfully.");
        //} else {
        //    return ResponseEntity.badRequest().body("Invalid request. The provided ID does not match the item's ID.");
        //}
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
*/

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
    public ResponseEntity<List<Item>> itemList(@PathVariable(name="category") String category) {
        Optional<List<Item>> items = itemService.findByCategory(category);

        if (items.isPresent()){
            return ResponseEntity.ok(items.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/isSoldout/{isSoldout}")
    public ResponseEntity<List<Item>> itemList(@PathVariable(name="isSoldout") Boolean isSoldout) {
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
        int result =  itemService.saveItem(item);
        if (result != 0) {
            return ResponseEntity.ok("Item created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Item.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody Item item) {
        item.setItemId(id); //item에 id를 넣지 않고 update해도 url의 id를 가진 item을 update하도록 함
        int result = itemService.updateItem(item);
        if (result != 0){
            return ResponseEntity.ok("Item updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("존재하지 않는 itemId이거나 update된 사항이 없습니다.");
        }
    }

    @PutMapping("/{id}/isSoldout")
    public ResponseEntity<String> updateIsSoldout(@PathVariable(name = "id") Long id) {
        int result = itemService.updateIsSoldout(id);
        if (result != 0){
            return ResponseEntity.ok("Item isSoldout updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("존재하지 않는 itemId이거나 update 오류가 발생했습니다.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        int result = itemService.deleteItem(id);
        if (result != 0){
            return ResponseEntity.ok("Item deleted successfully.");
        }
        else { //해당 id가 존재하지 않아 삭제 동작이 필요하지 않았던 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 itemId입니다.");
        }
    }
}

//item의 id가 null이면 안됨 => @Valid를 contorller에, @NotNull을 dto에 넣어주기!!!