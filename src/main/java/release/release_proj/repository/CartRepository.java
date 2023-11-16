package release.release_proj.repository;

import org.apache.ibatis.annotations.Param;
import release.release_proj.domain.Cart;

import java.util.List;
import java.util.Optional;

//@Repository
public interface CartRepository {

    public int deleteByMemberId(String memberId); //user의 장바구니 전체 삭제
    public int deleteByMemberIdAndItemId(@Param("memberId") String memberId, @Param("itemId") Long itemId); //user의 장바구니 중 특정 item만 삭제
    public int save(Cart cart);
    Optional<List<Cart>> findByMemberId(String memberId);
    //Optional<Cart> findOneByMemberIdAndItemId(@Param("memberId") String memberId, @Param("itemId") Long itemId);
    Optional<List<Cart>> findByMemberIdAndItemId(@Param("memberId") String memberId, @Param("itemId") Long itemId);
    //public int updateCartAmount(@Param("memberId") String memberId, @Param("itemId") Long itemId, @Param("amount") int amount);
    //public int deleteCartIfAmountIsZero(@Param("memberId") String memberId, @Param("itemId") Long itemId);
    public int updateCartAmount(@Param("cartId") Long cartId, @Param("amount") int amount);
    public int deleteCartIfAmountIsZero(Long cartId);

    /*public void deleteByCartId(Long cartId);
    Cart save(Cart cart);
    Optional<Cart> findByCartId(Long cartId);
    Optional<Cart> findByUserId(Long userId);*/
    
    //나중에 로그인 정보 확인해야 함
}
