package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Cart;
import release.release_proj.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //생성자 주입을 대신 해줌
public class CartServiceImpl implements CartService{

    //@Autowired
    //private CartRepository cartRepository; //필드주입

    private final CartRepository cartRepository;

    /*@Autowired //생성자 주입
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }*/

    @Override
    public Optional<List<Cart>> readMemberCarts(String memberId) {
        //return cartRepository.findByMemberId(member.getMemberId());
        return cartRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<Cart> readMemberCartItems(String memberId, Long itemId) {
        //return cartRepository.findByMemberIdAndItemId(member.getMemberId(), item.getItemId());
        return cartRepository.findByMemberIdAndItemId(memberId, itemId);
    }

    @Override
    public int addCartItem(Cart cart) {
        try {
            Optional<Cart> isCartExist = cartRepository.findByMemberIdAndItemId(cart.getMemberId(), cart.getItemId());
            if (isCartExist.isPresent()){
                Cart currentCart = isCartExist.get();
                currentCart.setAmount(currentCart.getAmount() + cart.getAmount());
                return cartRepository.updateCartAmount(currentCart.getCartId(), currentCart.getAmount());
            }
            else {
                return cartRepository.save(cart);
            }
        } catch (DataIntegrityViolationException e) {
            // 외래 키 제약 조건 위배로 인한 예외 처리
            throw new IllegalArgumentException("Failed to create cart. 해당하는 itemId나 memberId가 존재하지 않습니다", e);
        }
    }

    @Override
    public int deleteCartItem(String memberId, Long itemId) {
        //String memberId = member.getMemberId();
        //Long itemId = item.getItemId();
        Optional<Cart> isCartItemExist = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (isCartItemExist.isPresent()) {
            return cartRepository.deleteByMemberIdAndItemId(memberId, itemId);
        } else {
            return 0;
        }
    }

    @Override
    public int deleteCart(String memberId) {
        //String memberId = member.getMemberId();
        Optional<List<Cart>> memberCarts = cartRepository.findByMemberId(memberId);

        if (memberCarts.isPresent()) {
            return cartRepository.deleteByMemberId(memberId);
        } else {
            return 0;
        }
    }

    @Override
    public int decreaseCartItem(Long cartId) {
        int amount = -1;
        int result = cartRepository.updateCartAmount(cartId, amount);
        cartRepository.deleteCartIfAmountIsZero(cartId); //이게 제대로 작동하지 않았을 때의 예외처리를 해주어야 할까?

        return result;
    }

    @Override
    public int increaseCartItem(Long cartId, int amount) { //증가의 경우 상품페이지에서 바로 담을 수 있으므로 한번에 여러개의 상품이 증가할 수 있음

        return cartRepository.updateCartAmount(cartId, amount);
    }

}