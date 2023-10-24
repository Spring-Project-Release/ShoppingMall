package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Item;
import release.release_proj.repository.ItemRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

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
