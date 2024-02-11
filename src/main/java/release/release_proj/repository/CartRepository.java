package release.release_proj.repository;

import release.release_proj.domain.Cart;

import java.util.List;
import java.util.Optional;

//@Repository
public interface CartRepository {

    public int deleteByMemberId(String memberId); //user의 장바구니 전체 삭제
    public int deleteByMemberIdAndItemId(String memberId, Long itemId); //user의 장바구니 중 특정 item만 삭제
    public void save(Cart cart);
    Optional<List<Cart>> findByMemberId(String memberId, int offset, int limit);
    Optional<Cart> findByMemberIdAndItemId(String memberId, Long itemId);
    public int updateCartAmount(Long cartId, int amount);
    public int deleteCartIfAmountIsZero(Long cartId);
    
    //나중에 로그인 정보 확인해야 함
}
