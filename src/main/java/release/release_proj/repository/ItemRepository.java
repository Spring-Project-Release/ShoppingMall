package release.release_proj.repository;

import release.release_proj.domain.Item;

import java.util.List;
import java.util.Optional;

//@Repository
public interface ItemRepository {

    public Long deleteByItemId(Long itemId);
    public Item save(Item item);
    public Optional<Item> findByItemId(Long itemId);
    public Optional<Item> findByItemName(String name);
    public List<Item> findAll();
    public List<Item> findByIsSoldout(boolean isSoldout); //품절이 안된 상품들만 가져오기
    public List<Item> findByCategory(String category);
    public int updateItem(Item item);

    public int isSoldout(Long itemId);
}
