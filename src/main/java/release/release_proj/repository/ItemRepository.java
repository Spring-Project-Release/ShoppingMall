package release.release_proj.repository;

import release.release_proj.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    public void deleteByItemId(Long itemId);
    public void save(Item item);
    public Optional<Item> findByItemId(Long itemId);
    public Optional<Item> findByItemName(String name);
    public List<Item> findAll(int offset, int limit);
    public List<Item> findAllOrderByCreatedAtDesc(int offset, int limit);
    public List<Item> findAllOrderByCountDesc(int offset, int limit);
    public List<Item> findByIsSoldout(Boolean isSoldout, int offset, int limit); //품절이 안된 상품들만 가져오기
    public List<Item> findByCategory(String category, int offset, int limit);
    public List<Item> findBySellerId(String sellerId, int offset, int limit);
    public void updateItem(Item item);
    public int updateIsSoldout(Long itemId);
    public int updateStock(Long itemId, int decreasingStock);
    public int updateCount(Long itemId, int increasingCount);
    public int getStock(Long itemId);
    public int getPrice(Long itemId);
    public int isItemIdExist(Long itemId);
}
