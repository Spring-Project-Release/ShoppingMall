package release.release_proj.service;

import release.release_proj.domain.Item;

import java.util.List;

public interface ItemService {

    public int saveItem(Item item);
    public void isItemNameDuplicate(Item item);
    public int updateItem(Item item);
    public int deleteItem(Long itemId);
    public Item findOne(Long itemId);
    public List<Item> readItems();
    public List<Item> findItemsBySellerId(String sellerId);
    public List<Item> findItemsBySellerName(String sellerName);
    public int updateIsSoldout(Long itemId); //item에서 isSoldout만 반대값으로 update
    public int updateStock(Long itemId, int decreasingStock); //item에서 재고수만 감소
    public int updateCount(Long itemId, int increasingCount);
    public int getStock(Long itemId);
    public int getPrice(Long itemId);
    public List<Item> findByIsSoldout(Boolean isSoldout);
    public List<Item> findByCategory(String category);
    //public void updateStock(Item item, int newStock); //item에서 stock 값만 update
    public int isItemIdExist(Long itemId);
}
