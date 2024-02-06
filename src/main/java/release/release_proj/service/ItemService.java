package release.release_proj.service;

import release.release_proj.dto.ItemRequestDTO;
import release.release_proj.dto.ItemResponseDTO;

import java.util.List;

public interface ItemService {

    public int saveItem(ItemRequestDTO itemDTO);
    public void isItemNameDuplicate(ItemRequestDTO itemDTO);
    public int updateItem(ItemRequestDTO itemDTO);
    public int deleteItem(Long itemId);
    public ItemResponseDTO findOne(Long itemId);
    public ItemResponseDTO findByItemName(String name);
    public List<ItemResponseDTO> readItems();
    public List<ItemResponseDTO> findItemsBySellerId(String sellerId);
    public int updateIsSoldout(Long itemId); //item에서 isSoldout만 반대값으로 update
    public int updateStock(Long itemId, int decreasingStock); //item에서 재고수만 감소
    public int updateCount(Long itemId, int increasingCount);
    public int getStock(Long itemId);
    public int getPrice(Long itemId);
    public List<ItemResponseDTO> findByIsSoldout(Boolean isSoldout);
    public List<ItemResponseDTO> findByCategory(String category);
    //public void updateStock(Item item, int newStock); //item에서 stock 값만 update
    public int isItemIdExist(Long itemId);
}
