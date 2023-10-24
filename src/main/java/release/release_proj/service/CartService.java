package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Cart;
import release.release_proj.domain.CartItem;
import release.release_proj.domain.Item;
import release.release_proj.domain.User;
import release.release_proj.repository.CartItemRepository;
import release.release_proj.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public void addCart(User user, Item item, int count){
        cartRepository.findByUserId(user.getUserId())
            .ifPresentOrElse(
                cartExist -> {
                    // 아이템이 존재할 때 실행할 코드
                },
                () -> {
                    // 아이템이 존재하지 않을 때 실행할 코드
                }
        );
        //cart가 비어있다면 생성

        Optional<CartItem> cartItem = cartItemRepository.findByCartItemId(item.getItemId());

        //cartItem이 비어있다면 생성
        
        //cartItem이 비어있지 않다면 carItem의 수량만 증가
    }

    public void deleteCart(Long userId){ //장바구니 상품들 전체 삭제
        List<CartItem> cartItems = cartItemRepository.findAll(); //전체 cartItem 찾기

        for (CartItem cartItem : cartItems){
            if (cartItem.getCart().getUser().getUserId() == userId) {
                cartItem.setCount(cartItem.getCount()-1); //장바구니에 담긴 해당 상품 개수 하나 감소시키기
                cartItem.getCart().setCount(cartItem.getCart().getCount()-1); //장바구니에 담긴 상품 개수 하나 감소시키기
                cartItemRepository.deleteByCartItemId(cartItem.getCartItemId());
            }
        }
    }

    public void deleteCartItem(Long cartItemId){ //장바구니 중 특정 상품 삭제
        cartItemRepository.findByCartItemId(cartItemId).ifPresent(deletingCartItem -> {
            deletingCartItem.setCount(deletingCartItem.getCount()-1); //장바구니에 담긴 해당 상품 개수 하나 감소시키기
        });
        cartItemRepository.deleteByCartItemId(cartItemId);
    }

    public List<CartItem> readCartItems(Cart cart){ //장바구니 상품들 전체 조회
        List<CartItem> cartItems = cartItemRepository.findAll();
        //user 정보 일치하는지 확인하기
        
        return cartItems;
    }

    public void payCart(Long UserId){ //장바구니 결제 함수

    }
}
