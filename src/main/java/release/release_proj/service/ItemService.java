package release.release_proj.service;

/*
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public Long saveItem(Item item) {
        isItemNameDuplicate(item);
        itemRepository.save(item);
        return item.getItemId();
    }

    private void isItemNameDuplicate(Item item) {
        itemRepository.findByItemName(item.getName())
            .ifPresent(i -> {
                throw new IllegalStateException("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
            });
    }

    public void updateItem(Item item) {
        itemRepository.findByItemId(item.getItemId()).ifPresent(updatingItem -> { //!!!itemId가 없을 경우 추후 예외처리해야함!!!!
            updatingItem.setName(item.getName());
            updatingItem.setPrice(item.getPrice());
            updatingItem.setText(item.getText());
            updatingItem.setStock(item.getStock());
            updatingItem.setItem_type(item.getItem_type());
            itemRepository.save(updatingItem); //isItemNameDuplicate도 적용됨
        });
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteByItemId(itemId);
    }

    public Optional<Item> findOne(Long itemId) {
        return itemRepository.findByItemId(itemId);
    }

    public List<Item> readItems() {
        return itemRepository.findAll();
    }

}
*/

import release.release_proj.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    public int saveItem(Item item);
    public void isItemNameDuplicate(Item item);
    public int updateItem(Item item);
    public int deleteItem(Long itemId);
    public Optional<Item> findOne(Long itemId);
    public Optional<List<Item>> readItems();
    public int updateIsSoldout(Long itemId); //item에서 isSoldout만 반대값으로 update
    public int updateStock(Long itemId, int decreasingStock); //item에서 재고수만 감소
    public int updateCount(Long itemId, int increasingCount);
    public int getStock(Long itemId);
    public int getPrice(Long itemId);
    public Optional<List<Item>> findByIsSoldout(Boolean isSoldout);
    public Optional<List<Item>> findByCategory(String category);
    //public void updateStock(Item item, int newStock); //item에서 stock 값만 update
    public int isItemIdExist(Long itemId);
}
