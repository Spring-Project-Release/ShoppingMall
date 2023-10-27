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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public void addCartItem(User user, Cart cart, Item item, int count) {
        cartItemRepository.findByCartIdAndItemId(cart.getCartId(),item.getItemId())
                .ifPresentOrElse(
                        cartItemExist -> {  //cartItem이 비어있지 않다면 carItem과 cart의 count만 증가
                            cartItemExist.setCount(cartItemExist.getCount()+1);
                            cartItemExist.getCart().setCount(cartItemExist.getCart().getCount()+1);
                        },
                        ()->{ //cartItem이 비어있다면 생성
                            CartItem cartItem = CartItem.createCartItem(cart, item, count);
                            cartItemRepository.save(cartItem);
                            cart.setCount(cart.getCount()+count);
                        }
                );
    }

    public void addCart(User user, Item item, int count){
        /*cartRepository.findByUserId(user.getUserId())
            .ifPresentOrElse(
                cartExist -> {
                    //Cart cart = cartExist;
                },
                () -> {
                    Cart cart = Cart.createCart(user); //cart가 비어있다면 생성
                    cartRepository.save(cart);
                }
        );

        //cartItemRepository.findByCartItemId(item.getItemId())
        cartItemRepository.findByCartIdAndItemId(cart.getCartId(),item.getItemId())
                .ifPresentOrElse(
                    cartItemExist -> {  //cartItem이 비어있지 않다면 carItem과 cart의 count만 증가
                        cartItemExist.setCount(cartItemExist.getCount()+1);
                        cartItemExist.getCart().setCount(cartItemExist.getCart().getCount()+1);
                    },
                    ()->{ //cartItem이 비어있다면 생성
                        //cartRepository.findByUserId(user.getUserId())
                        //        .ifPresent(cart->{
                        //            CartItem cartItem = CartItem.createCartItem(cart,item);
                        //        });

                    }
                );
         */
        cartRepository.findByUserId(user.getUserId())
                .ifPresentOrElse(
                        cartExist -> {
                            addCartItem(user, cartExist, item, count);
                        },
                        () -> {
                            Cart cart = Cart.createCart(user); //cart가 비어있다면 생성
                            cartRepository.save(cart);
                            addCartItem(user, cart, item, count);
                        }
                );
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

    public void reduceCartItem(Long cartItemId){ //장바구니 중 특정 상품 한 개 삭제
        cartItemRepository.findByCartItemId(cartItemId).ifPresent(deletingCartItem -> {
            deletingCartItem.setCount(deletingCartItem.getCount()-1); //장바구니에 담긴 해당 상품 개수 하나 감소시키기
            deletingCartItem.getCart().setCount(deletingCartItem.getCart().getCount()-1); //장바구니에 담긴 상품 개수 하나 감소시키기
            cartItemRepository.deleteByCartItemId(cartItemId);
        });
        //없으면 error 처리..?
    }

    public void deleteCartItem(Long cartItemId){ //장바구니 중 특정 상품 전체 삭제
        cartItemRepository.findByCartItemId(cartItemId).ifPresent(cartItem -> {
            int count = cartItem.getCount();
            cartItem.getCart().setCount(cartItem.getCount()-count);
            cartItemRepository.deleteByCartItemId(cartItemId);
        });
        //없으면 error 처리..?
    }

    public List<CartItem> readCartItems(Cart cart){ //장바구니 상품들 전체 조회
        List<CartItem> cartItems = cartItemRepository.findAll();
        List<CartItem> userItems = new ArrayList<>();
        //cart와 cartItem 사이 user 정보 일치하는지 확인하기

        for (CartItem cartItem : cartItems) {
            if (cartItem.getCart().getCartId() == cart.getCartId()) {
                userItems.add(cartItem);
            }
        }
        
        return userItems;
    }

    public void payCart(Long UserId){ //장바구니 결제 함수

    }
}
