package release.release_proj.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import release.release_proj.domain.Cart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CartImpl implements CartRepository {

    @Autowired
    private SqlSession sqlSession;

    public static final String NS = "sql.cart.mapper.";

    @Override
    public void deleteByMemberId(Long memberId) {
        sqlSession.delete(NS + "deleteByMemberId", memberId);
    }

    @Override
    public void deleteByMemberIdAndItemId(@Param("memberId") Long memberId, @Param("itemId") Long itemId) {
        Map<String, Long> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        sqlSession.delete(NS + "deleteByMemberIdAndItemId", params);
    }

    @Override
    public Cart save(Cart cart) {
        sqlSession.insert(NS + "save", cart);
        return cart;
    }

    @Override
    public Optional<Cart> findByMemberId(Long memberId) {
        return Optional.ofNullable(sqlSession.selectOne(NS + "findByMemberId", memberId));
    }

    @Override
    public Optional<Cart> findByMemberIdAndItemId(@Param("memberId") Long memberId, @Param("itemId") Long itemId) {
        Map<String, Long> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        return Optional.ofNullable(sqlSession.selectOne(NS + "findByMemberIdAndItemId", params));
    }
}
