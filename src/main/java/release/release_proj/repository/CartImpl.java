package release.release_proj.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import release.release_proj.domain.Cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Cart save(Cart cart) {
        sqlSession.insert(NS + "save", cart);
        return cart;
    }

    /*@Override
    public Optional<Cart> findByMemberId(Long memberId) {
        return Optional.ofNullable(sqlSession.selectOne(NS + "findByMemberId", memberId));
    }
    */
    public Optional<List<Cart>> findByMemberId(String memberId) {
        return Optional.ofNullable(sqlSession.selectList(NS + "findByMemberId", memberId));
    }

    /*@Override
    public Optional<Cart> findByMemberIdAndItemId(@Param("memberId") String memberId, @Param("itemId") Long itemId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        return Optional.ofNullable(sqlSession.selectOne(NS + "findByMemberIdAndItemId", params));
    }*/


    public Optional<List<Cart>> findByMemberIdAndItemId(@Param("memberId") String memberId, @Param("itemId") Long itemId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("itemId", itemId);
        return Optional.ofNullable(sqlSession.selectList(NS + "findByMemberIdAndItemId", params));
    }
}
