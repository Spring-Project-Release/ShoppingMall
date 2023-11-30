package release.release_proj.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import release.release_proj.domain.Cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CartImpl implements CartRepository {

    @Autowired
    private SqlSession sqlSession;

    public static final String NS = "sql.cart.mapper.";

    @Override
    public int deleteByMemberId(String memberId) {
        return sqlSession.delete(NS + "deleteByMemberId", memberId);
    }

    @Override
    public int deleteByMemberIdAndItemId(@Param("memberId") String memberId, @Param("itemId") Long itemId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        return sqlSession.delete(NS + "deleteByMemberIdAndItemId", params);
    }

    @Override
    public int save(Cart cart) {
        return sqlSession.insert(NS + "save", cart);
    }

    @Override
    public Optional<List<Cart>> findByMemberId(String memberId) {
        return Optional.ofNullable(sqlSession.selectList(NS + "findByMemberId", memberId));
    }

    /*@Override
    public Optional<Cart> findOneByMemberIdAndItemId(@Param("memberId") String memberId, @Param("itemId") Long itemId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        return Optional.ofNullable(sqlSession.selectOne(NS + "findByMemberIdAndItemId", params));
    }*/

    @Override
    public Optional<Cart> findByMemberIdAndItemId(@Param("memberId") String memberId, @Param("itemId") Long itemId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        return Optional.ofNullable(sqlSession.selectOne(NS + "findByMemberIdAndItemId", params));
    }

    /*public int updateCartAmount(@Param("memberId") String memberId, @Param("itemId") Long itemId, @Param("amount") int amount) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        params.put("amount", amount);
        return sqlSession.update(NS+"updateCartAmount", params);
    }*/

    @Override
    public int updateCartAmount(@Param("cartId") Long cartId, @Param("amount") int amount) {
        Map<String, Object> params = new HashMap<>();
        params.put("cartId",cartId);
        params.put("amount", amount);
        return sqlSession.update(NS+"updateCartAmount", params);
    }

    /*@Override
    public int deleteCartIfAmountIsZero(@Param("memberId") String memberId, @Param("itemId") Long itemId){
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        return sqlSession.delete(NS+"deleteCartIfAmountIsZero", params);
    }*/

    @Override
    public int deleteCartIfAmountIsZero(Long cartId){
        return sqlSession.delete(NS+"deleteCartIfAmountIsZero", cartId);
    }
}
