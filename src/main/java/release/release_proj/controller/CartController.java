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
@RequiredArgsConstructor //생성자 주입을 자동으로 해줌
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping("/user/{memberId}")
    public ResponseEntity<List<Cart>> memberCartList(@PathVariable(name="memberId") String memberId) {
        Optional<List<Cart>> carts = cartService.readMemberCarts(memberId);

        if (carts.isPresent()){
            return ResponseEntity.ok(carts.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{memberId}/item/{itemId}")
    public ResponseEntity<Cart> memberCartItemList(@PathVariable(name="memberId") String memberId, @PathVariable(name="itemId") Long itemId) {
        Optional<Cart> cart = cartService.readMemberCartItems(memberId, itemId);

        if (cart.isPresent()){
            return ResponseEntity.ok(cart.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> newCart(@RequestBody Cart cart) {
        try {
            int result = cartService.addCartItem(cart);

            if (result != 0) {
                return ResponseEntity.ok("Cart created or amount updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create cart.");
            }
            //} catch (DataIntegrityViolationException e) {
            //    // 외래 키 제약 조건 위배로 인한 예외 처리
            //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create cart. 해당하는 itemId나 memberId가 존재하지 않습니다");
            //}
        } catch (IllegalArgumentException e) {
            // DataIntegrityViolationException이 IllegalArgumentException로 변환되었을 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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

    //@PutMapping("user/{memberId}/item/{itemId}")
    //public ResponseEntity<String> increaseCartItemAmount(@PathVariable(name = "memberId") String memberId, @PathVariable(name="itemId") Long itemId, @RequestBody Integer amount) {
    @PutMapping("/{cartId}")
    public ResponseEntity<String> increaseCartItemAmount(@PathVariable(name="cartId") Long cartId, @RequestBody(required = false) Integer amount) {
        int result;
        String successMessage;

        if (amount != null && amount > 0) {
            //result = cartService.increaseCartItem(memberId, itemId, amount);
            result = cartService.increaseCartItem(cartId, amount);
            successMessage = "User's cartItem amount increased successfully.";
        } else if (amount == null) {
            //result = cartService.decreaseCartItem(memberId, itemId);
            result = cartService.decreaseCartItem(cartId);
            successMessage = "User's cartItem amount decreased successfully.";
        } else {
            return ResponseEntity.badRequest().body("Invalid request. Please provide a valid amount for cartItem.");
        }

        if (result != 0) {
            return ResponseEntity.ok(successMessage);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The corresponding cart does not exist or an error occurred.");
        }
    }

    @PostMapping("/order/user/{memberId}")
    public ResponseEntity<String> payAllCart(@PathVariable(name="memberId") String memberId, @RequestBody(required = false) String memo) {
        try {
            cartService.payAllCart(memberId, memo);
            return ResponseEntity.ok("장바구니 결제가 성공적으로 처리되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 결제 처리 중 오류가 발생했습니다."+e);
        }
    }

    @PostMapping("/order/some/user/{memberId}")
    public ResponseEntity<String> paySomeCart(@PathVariable(name="memberId") String memberId, @RequestBody List<Long> ItemIds, @RequestBody(required = false) String memo) {
        try {
            cartService.paySomeCart(memberId, ItemIds, memo);
            return ResponseEntity.ok("장바구니 결제가 성공적으로 처리되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 결제 처리 중 오류가 발생했습니다."+e);
        }
    }
}
