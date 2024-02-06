package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaItemRepository implements ItemRepository {

    private final EntityManager em;

    public JpaItemRepository(EntityManager em) {
        this.em = em;
    };

    @Override
    public void deleteByItemId(Long itemId) {
        Item item = em.find(Item.class, itemId);
        if (item != null) {
            em.remove(item);
        }
    }

    @Override
    public void save(Item item) {
        em.persist(item);
    }

    @Override
    public Optional<Item> findByItemId(Long itemId) {
        Item item = em.find(Item.class, itemId);
        //조회할 type, 식별자인 pk
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Item> findByItemName(String name) {
        Item item = em.createQuery("SELECT i FROM Item i WHERE i.name = :name", Item.class)
                .setParameter("name", name)
                .getSingleResult();

        return Optional.ofNullable(item);
    }

    @Override
    public Optional<List<Item>> findBySellerId(String sellerId){
        List<Item> item = em.createQuery("select i from Item i where i.sellerId = :sellerId", Item.class)
                .setParameter("sellerId", sellerId)
                .getResultList();

        return Optional.ofNullable(item);
    }

    @Override
    public Optional<List<Item>> findAll() {
       List<Item> item = em.createQuery("select i from Item i", Item.class)
                .getResultList();

        return Optional.ofNullable(item);
    }

    @Override
    public Optional<List<Item>> findByIsSoldout(Boolean isSoldout) {
         List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.isSoldout = :isSoldout", Item.class)
                .setParameter("isSoldout", isSoldout)
                .getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<Item>> findByCategory(String category) {
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.category = :category", Item.class)
                .setParameter("category", category)
                .getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public void updateItem(Item item) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(item);
        transaction.commit();
    }

    @Override
    public int updateIsSoldout(Long itemId){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        int result = em.createQuery("UPDATE Item i SET i.isSoldout = 1 - i.isSoldout WHERE i.itemId = :itemId")
                .setParameter("itemId", itemId)
                .executeUpdate();

        transaction.commit();

        return result; //실행된 update query에 의해 영향을 받은 entity의 수
    }

    @Override
    public int updateStock(Long itemId, int decreasingStock){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        int result = em.createQuery("UPDATE Item i SET i.stock = i.stock - :decreasingStock WHERE i.itemId = :itemId")
                .setParameter("decreasingStock", decreasingStock)
                .setParameter("itemId", itemId)
                .executeUpdate();

        transaction.commit();

        return result;
    }

    @Override
    public int updateCount(Long itemId, int increasingCount) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        int result = em.createQuery("UPDATE Item i SET i.count = i.count + :increasingCount WHERE i.itemId = :itemId")
                .setParameter("increasingCount", increasingCount)
                .setParameter("itemId", itemId)
                .executeUpdate();

        transaction.commit();

        return result;
    }

    @Override
    public int getStock(Long itemId) {
        Item item = em.find(Item.class, itemId);
        return item.getStock();
    }

    @Override
    public int getPrice(Long itemId) {
        Item item = em.find(Item.class, itemId);
        return item.getPrice();
    }

    @Override
    public int isItemIdExist(Long itemId) {
        Item item = em.find(Item.class, itemId);
        return item != null ? 1 : 0;
    }
}
