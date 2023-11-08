package release.release_proj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Cart;
import release.release_proj.domain.Item;
import release.release_proj.domain.MemberVO;
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
    public Optional<List<Cart>> readMemberCarts(MemberVO member) {
        return cartRepository.findByMemberId(member.getMemberId());
    }

    @Override
    public Optional<List<Cart>> readMemberCartItems(MemberVO member, Item item) {
        return cartRepository.findByMemberIdAndItemId(member.getMemberId(), item.getItemId());
    }

    @Override
    public Long addCartItem(Cart cart) {
        Cart addedCartItem = cartRepository.save(cart);
        return addedCartItem.getCartId();
    }

    @Override
    public int deleteCartItem(MemberVO member, Item item) {
        String memberId = member.getMemberId();
        Long itemId = item.getItemId();
        Optional<List<Cart>> isCartItemExist = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (isCartItemExist.isPresent()) {
            return cartRepository.deleteByMemberIdAndItemId(memberId, itemId);
        } else {
            return 0;
        }
    }

    @Override
    public int deleteCart(MemberVO member) {
        String memberId = member.getMemberId();
        Optional<List<Cart>> memberCarts = cartRepository.findByMemberId(memberId);

        if (memberCarts.isPresent()) {
            return cartRepository.deleteByMemberId(memberId);
        } else {
            return 0;
        }
    }

    @Override
    public int decreaseCartItem(MemberVO member, Item item) {
        String memberId = member.getMemberId();
        Long itemId = item.getItemId();
        int amount = -1;
        return cartRepository.updateCartAmount(memberId, itemId, amount);
    }
    //이때 decrease를 해서 amount값이 0이 되면 해당 데이터를 삭제하도록 해야 함

    @Override
    public int increaseCartItem(MemberVO member, Item item) {
        String memberId = member.getMemberId();
        Long itemId = item.getItemId();
        int amount = 1;
        return cartRepository.updateCartAmount(memberId, itemId, amount);
    }
}
