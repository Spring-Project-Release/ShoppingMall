package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.CartItem;
import release.release_proj.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    //cartId
    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemList(@PathVariable(name = "cartId") Long cartId) {
        //cartService.readCartItems()
        //findAll이랑 readCartItems cart id 확인하고 하도록 바꾸기
    }

    ///user/userId/cart


    //cartItem/cartItemId
    @PostMapping("/cartItem")
    public ResponseEntity<CartItem> getCartItem(@RequestBody CartItem cartItem, @RequestBody int count) {
        Long saveCartItemId = cartService.addCart(null,cartItem, count); //user에 임시로 null 넣어놓음
        /*
        Long saveId = itemService.saveItem(item);
        return ResponseEntity.ok("Item created successfully.");
         */
    }
}
