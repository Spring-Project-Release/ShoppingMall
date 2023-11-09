package release.release_proj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Cart;
import release.release_proj.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    //@Autowired
    //private CartRepository cartRepository;

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Optional<List<Cart>> readMemberCarts(String memberId) {
        //return cartRepository.findByMemberId(member.getMemberId());
        return cartRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<List<Cart>> readMemberCartItems(String memberId, Long itemId) {
        //return cartRepository.findByMemberIdAndItemId(member.getMemberId(), item.getItemId());
        return cartRepository.findByMemberIdAndItemId(memberId, itemId);
    }

    @Override
    public int addCartItem(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public int deleteCartItem(String memberId, Long itemId) {
        //String memberId = member.getMemberId();
        //Long itemId = item.getItemId();
        Optional<List<Cart>> isCartItemExist = cartRepository.findByMemberIdAndItemId(memberId, itemId);

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
    public int decreaseCartItem(String memberId, Long itemId) {
        //String memberId = member.getMemberId();
        //Long itemId = item.getItemId();
        int amount = -1;
        cartRepository.updateCartAmount(memberId, itemId, amount);

        return cartRepository.deleteCartIfAmountIsZero(memberId, itemId);
    }
    //decrease를 해서 amount값이 0이 되면 해당 데이터를 삭제하도록 구현하였음
    //아니면 값이 amount가 2 이상일 때만 decreaseCartItem 함수를 실행할 수 있도록 바꿀까..?

    @Override
    public int increaseCartItem(String memberId, Long itemId, int amount) { //증가의 경우 상품페이지에서 바로 담을 수 있으므로 한번에 여러개의 상품이 증가할 수 있음
        //String memberId = member.getMemberId();
        //Long itemId = item.getItemId();

        return cartRepository.updateCartAmount(memberId, itemId, amount);
    }
}
//mybatis에서 get...를 사둉하는 게 좋을지 생각해볼것