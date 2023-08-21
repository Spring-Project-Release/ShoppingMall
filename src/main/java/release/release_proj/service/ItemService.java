package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Item;
import release.release_proj.repository.ItemRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        isItemNameDuplicate(item);
        itemRepository.save(item);
    }

    private void isItemNameDuplicate(Item item) {
        itemRepository.findByName(item.getName())
            .ifPresent(i -> {
                throw new IllegalStateException("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
            });
    }

    public void updateItem(Item item) {
        itemRepository.findById(item.getId()).ifPresent(updatingItem -> {
            updatingItem.setName(item.getName()); //isItemNameDuplicate 적용
            updatingItem.setPrice(item.getPrice());
            updatingItem.setText(item.getText());
            updatingItem.setStock(item.getStock());
            updatingItem.setItem_type(item.getItem_type());
            itemRepository.save(updatingItem);
        });
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Optional<Item> findOne(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public List<Item> readItems() {
        return itemRepository.findAll();
    }

}
