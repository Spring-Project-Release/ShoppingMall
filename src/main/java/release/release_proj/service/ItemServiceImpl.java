package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Item;
import release.release_proj.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public int saveItem(Item item){
        isItemNameDuplicate(item);
        return itemRepository.save(item);
    }
    //!!!해당 seller가 실제로 존재하는 seller인지도 확인해봐야함!!!-외래키제약위배여부 확인(db에도 조건 추가)
    //!!!cart에서 해당 item의 seller가 cart의 memberId랑 겹치지 않도록 해야함!!!

    @Override
    public void isItemNameDuplicate(Item item){
        itemRepository.findByItemName(item.getName())
                .ifPresent(i -> {
                    throw new IllegalStateException("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
                });
    }

    @Override
    public int updateItem(Item item){
        return itemRepository.updateItem(item);
    }

    @Override
    public int deleteItem(Long itemId){
        return itemRepository.deleteByItemId(itemId);
    }

    @Override
    public Item findOne(Long itemId) {
        return itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 itemId를 가진 상품이 존재하지 않습니다."));
    }

    @Override
    public List<Item> readItems() {
        Optional<List<Item>> items =  itemRepository.findAll();
        if (items.isEmpty() || items.get().isEmpty()) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        return items.get();
    }

    @Override
    public List<Item> findItemsBySellerId(String sellerId) {
        Optional<List<Item>> items = itemRepository.findBySellerId(sellerId);

        if (items.isEmpty() || items.get().isEmpty()) {
            throw new IllegalArgumentException("해당 sellerId를 가진 상품이 존재하지 않습니다.");
        }

        return items.get();
    }

    @Override
    public List<Item> findItemsBySellerName(String sellerName) {
        Optional<List<Item>> items = itemRepository.findBySellerName(sellerName);

        if (items.isEmpty() || items.get().isEmpty()) {
            throw new IllegalArgumentException("해당 sellerName을 가진 상품이 존재하지 않습니다.");
        }

        return items.get();
    }

    @Override
    public List<Item> findByIsSoldout(Boolean isSoldout) {
        Optional<List<Item>> items = itemRepository.findByIsSoldout(isSoldout);

        if (items.isEmpty() || items.get().isEmpty()) {
            throw new IllegalArgumentException("해당 품절여부 조건을 만족하는 상품이 존재하지 않습니다.");
        }

        return items.get();
    }

    @Override
    public List<Item> findByCategory(String category) {
        Optional<List<Item>> items = itemRepository.findByCategory(category);

        if (items.isEmpty() || items.get().isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리를 가진 상품이 존재하지 않습니다.");
        }

        return items.get();
    }

    @Override
    public int updateIsSoldout(Long itemId) { //나중에 itemName을 인자로 사용할수도?
        return itemRepository.updateIsSoldout(itemId);
    }

    @Override
    public int updateStock(Long itemId, int decreasingStock) {
        return itemRepository.updateStock(itemId, decreasingStock);
    }

    @Override
    public int getStock(Long itemId) {
        return itemRepository.getStock(itemId);
    }

    @Override
    public int getPrice(Long itemId) {
        return itemRepository.getPrice(itemId);
    }

    @Override
    public int updateCount(Long itemId, int increasingCount){
        return itemRepository.updateCount(itemId, increasingCount);
    }

    @Override
    public int isItemIdExist(Long itemId) {
        return itemRepository.isItemIdExist(itemId);
    }
}
