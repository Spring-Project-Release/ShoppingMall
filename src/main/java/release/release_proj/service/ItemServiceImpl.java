package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Item;
import release.release_proj.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public int saveItem(Item item){
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

    @Override
    public int updateItem(Item item){
        return itemRepository.updateItem(item);
    }

    @Override
    public int deleteItem(Long itemId){
        return itemRepository.deleteByItemId(itemId);
    }

    @Override
    public Optional<Item> findOne(Long itemId){ //나중에 itemName을 인자로 사용할수도?
        Optional<Item> item = itemRepository.findByItemId(itemId);
        if (item.isPresent()) {
            return item;
        } else {
            throw new IllegalStateException("해당 itemId를 가진 상품이 존재하지 않습니다.");
        }
    }

    @Override
    public Optional<List<Item>> readItems(){
        return itemRepository.findAll();
    }

    public Optional<List<Item>> findByIsSoldout(boolean isSoldout) {
        return itemRepository.findByIsSoldout(isSoldout);
    }

    public Optional<List<Item>> findByCategory(String category){
        return itemRepository.findByCategory(category);
    }

    @Override
    public int updateIsSoldout(Long itemId) { //나중에 itemName을 인자로 사용할수도?
        return itemRepository.isSoldout(itemId);
    }

    /*@Override //추후 필요하면 update
    public void updateStock(Item item, int newStock){

    }*/

}
