package release.release_proj.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import release.release_proj.domain.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemImpl implements ItemRepository {

    @Autowired
    private SqlSession sqlSession;

    public static final String NS = "sql.item.mapper.";

    @Override
    public int deleteByItemId(Long itemId) {
        sqlSession.delete("sql.cart.mapper.deleteByItemId", itemId); //해당하는 cart부터 먼저 삭제
        return sqlSession.delete(NS + "deleteByItemId", itemId);
    }

    @Override
    public int save(Item item) {
        return sqlSession.insert(NS + "save", item);
    }

    @Override
    public Optional<Item> findByItemId(Long itemId) {
        return Optional.ofNullable(sqlSession.selectOne(NS + "findByItemId", itemId));
    }

    @Override
    public Optional<Item> findByItemName(String name) {
        return Optional.ofNullable(sqlSession.selectOne(NS + "findByItemName", name));
    }

    @Override
    public Optional<List<Item>> findBySellerId(String sellerId){
        return Optional.ofNullable(sqlSession.selectList(NS + "findBySellerId", sellerId));
    }

    @Override
    public Optional<List<Item>> findAll() {
        return Optional.ofNullable(sqlSession.selectList(NS + "findAll"));
    }

    @Override
    public Optional<List<Item>> findByIsSoldout(Boolean isSoldout) {
        return Optional.ofNullable(sqlSession.selectList(NS + "findByIsSoldout", isSoldout));
    }

    @Override
    public Optional<List<Item>> findByCategory(String category) {
        return Optional.ofNullable(sqlSession.selectList(NS + "findByCategory", category));
    }

    @Override
    public int updateItem(Item item) {
        return sqlSession.update(NS + "updateItem", item);
    }

    @Override
    public int updateIsSoldout(Long itemId){
        return sqlSession.update(NS + "updateIsSoldout", itemId);
    }

    @Override
    public int updateStock(@Param("itemId") Long itemId, @Param("decreasingStock") int decreasingStock){
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        params.put("decreasingStock", decreasingStock);
        return sqlSession.update(NS + "updateStock", params);
    }

    @Override
    public int updateCount(@Param("itemId") Long itemId, @Param("increasingCount") int increasingCount){
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        params.put("increasingCount", increasingCount);
        return sqlSession.update(NS + "updateCount", params);
    }

    @Override
    public int getStock(Long itemId) {
        return sqlSession.selectOne(NS + "getStock", itemId);
    }

    @Override
    public int getPrice(Long itemId) {
        return sqlSession.selectOne(NS + "getPrice", itemId);
    }

    @Override
    public int isItemIdExist(Long itemId) {
        return sqlSession.selectOne(NS + "isItemIdExist", itemId);
    }
}
