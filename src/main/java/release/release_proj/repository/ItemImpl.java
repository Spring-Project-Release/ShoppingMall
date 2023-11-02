package release.release_proj.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import release.release_proj.domain.Item;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemImpl implements ItemRepository {

    @Autowired
    private SqlSession sqlSession;

    public static final String NS = "sql.item.mapper.";

    @Override
    public Long deleteByItemId(Long itemId) {
        sqlSession.delete(NS + "deleteByItemId", itemId);
        return itemId;
    }

    @Override
    public Item save(Item item) {
        sqlSession.insert(NS + "save", item);
        return item;
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
    public List<Item> findAll() {
        return sqlSession.selectList(NS + "findAll");
    }

    @Override
    public List<Item> findByIsSoldout(boolean isSoldout) {
        return sqlSession.selectList(NS + "findByIsSoldout", isSoldout);
    }

    @Override
    public List<Item> findByCategory(String category) {
        return sqlSession.selectList(NS + "findByCategory", category);
    }

    @Override
    public int updateItem(Item item) {
        return sqlSession.update(NS + "updateItem", item);
    }

    @Override
    public int isSoldout(Long itemId){
        return sqlSession.update(NS + "isSoldout", itemId);
    }
}
    //@Autowired
    //private ItemRepository itemMapper;

    /*@Override
    public void deleteByItemId(Long itemId){
        itemMapper.deleteByItemId(itemId);
    }

    @Override
    public Item save(Item item){
        itemMapper.save(item);
        return item;
    }

    @Override
    public Optional<Item> findByItemId(Long itemId){
        //return Optional.ofNullable(itemMapper.findByItemId(itemId));
        return itemMapper.findByItemId(itemId);
    }

    @Override
    public Optional<Item> findByItemName(String name){
        //return Optional.ofNullable(itemMapper.findByItemName(name));
        return itemMapper.findByItemName(name);
    }

    @Override
    public List<Item> findAll(){
        return itemMapper.findAll();
    }

    @Override
    public List<Item> findByIsSoldout(boolean isSoldout){
        return itemMapper.findByIsSoldout(isSoldout);
    }

    @Override
    public List<Item> findByCategory(String category){
        return itemMapper.findByCategory(category);
    }

    @Override
    public void updateItem(Item item) {
        itemMapper.updateItem(item);
    }
}*/
