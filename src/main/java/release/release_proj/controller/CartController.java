package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.Cart;
import release.release_proj.service.CartService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping("/user/{memberId}")
    public ResponseEntity<List<Cart>> cartList(@PathVariable(name="memberId") String memberId) {
        Optional<List<Cart>> carts = cartService.readMemberCarts(memberId);

        if (carts.isPresent()){
            return ResponseEntity.ok(carts.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{memberId}/item/{itemId}")
    public ResponseEntity<List<Cart>> cartList(@PathVariable(name="memberId") String memberId, @PathVariable(name="itemId") Long itemId) {
        Optional<List<Cart>> carts = cartService.readMemberCartItems(memberId, itemId);

        if (carts.isPresent()){
            return ResponseEntity.ok(carts.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> newCart(@RequestBody Cart cart) {
        int result = cartService.addCartItem(cart);
        if (result != 0){
            return ResponseEntity.ok("Cart created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create cart.");
        }
    }

    @DeleteMapping("/user/{memberId}")
    public ResponseEntity<String> deleteCart(@PathVariable(name = "memberId") String memberId) {
        int result = cartService.deleteCart(memberId);
        if (result != 0){
            return ResponseEntity.ok("User's cart deleted successfully.");
        } else { //해당하는 cartId가 존재하지 않아 삭제 동작이 필요하지 않았던 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 cartId가 존재하지 않습니다");
        }
    }

    @DeleteMapping("/user/{memberId}/item/{itemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable(name = "memberId") String memberId, @PathVariable(name="itemId") Long itemId) {
        int result = cartService.deleteCartItem(memberId, itemId);
        if (result != 0){
            return ResponseEntity.ok("User's cartItem deleted successfully.");
        } else { //해당하는 cartId가 존재하지 않아 삭제 동작이 필요하지 않았던 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 cartId가 존재하지 않습니다");
        }
    }

    @PutMapping("user/{memerId}/item/{itemId}")
    public ResponseEntity<String> decreaseCartItemAmount(@PathVariable(name = "memberId") String memberId, @PathVariable(name="itemId") Long itemId) {
        int result = cartService.decreaseCartItem(memberId, itemId);
        if (result != 0){
            return ResponseEntity.ok("User's cartItem amount decreased successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("해당하는 cart가 존재하지 않거나 오류가 발생했습니다.");
        }
    }

    @PutMapping("user/{memerId}/item/{itemId}")
    public ResponseEntity<String> increaseCartItemAmount(@PathVariable(name = "memberId") String memberId, @PathVariable(name="itemId") Long itemId, @RequestBody int amount) {
        int result = cartService.increaseCartItem(memberId, itemId, amount);
        if (result != 0){
            return ResponseEntity.ok("User's cartItem amount increased successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("해당하는 cart가 존재하지 않거나 오류가 발생했습니다.");
        }
    }
}
