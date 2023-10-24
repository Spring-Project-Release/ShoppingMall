package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import release.release_proj.service.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    //cart/cartId

    //cart/user/userId/cart

    //cart/cartItem/cartItemId
}
