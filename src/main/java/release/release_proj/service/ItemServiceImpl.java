package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Item;
import release.release_proj.repository.ItemRepository;
import release.release_proj.repository.MemberDAO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberDAO memberDAO;

    @Override
    public int saveItem(Item item){
        validateSellerExistence(item.getSellerId()); //해당 seller가 실제로 존재하는 유저인지 확인
        isItemNameDuplicate(item);
        return itemRepository.save(item);
    }

    @Override
    public void isItemNameDuplicate(Item item){
        itemRepository.findByItemName(item.getName())
                .ifPresent(i -> {
                    throw new IllegalStateException("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
                });
    }

    private void validateSellerExistence(String sellerId) {
        if (memberDAO.isExistMemberId(sellerId)==0) {
            throw new IllegalArgumentException("해당하는 상품의 sellerId가 유저 DB에 존재하지 않습니다.");
        }
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
    public Item findByItemName(String name) {
        return itemRepository.findByItemName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 name을 가진 상품이 존재하지 않습니다."));
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
