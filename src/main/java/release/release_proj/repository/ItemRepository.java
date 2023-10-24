package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.Item;

import java.util.List;
import java.util.Optional;

//@Repository
public interface ItemRepository {

    //Item update(Item item);
    //void delete(Item item);
    public void deleteByItemId(Long itemId);
    Item save(Item item);
    Optional<Item> findByItemId(Long itemId);
    Optional<Item> findByItemName(String name);
    List<Item> findAll();
}
