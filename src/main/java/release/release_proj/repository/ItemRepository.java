package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    //Item update(Item item);
    //void delete(Item item);
    public void deleteById(Long id);
    Item save(Item item);
    Optional<Item> findById(Long id);
    Optional<Item> findByName(String name);
    List<Item> findAll();
}
