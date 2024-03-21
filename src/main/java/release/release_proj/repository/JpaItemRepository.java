package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Item;

import javax.persistence.EntityManager;
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
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.name = :name", Item.class)
                .setParameter("name", name)
                .getResultList();

        if (items.isEmpty()) {
            return Optional.ofNullable(null);
        } else {
            return Optional.ofNullable(items.get(0));
        }
    }

    @Override
    public List<Item> findBySellerId(String sellerId, int offset, int limit){
        return em.createQuery("select i from Item i where i.sellerId = :sellerId", Item.class)
                .setParameter("sellerId", sellerId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public List<Item> findAll(int offset, int limit) {
        return em.createQuery("select i from Item i", Item.class)
               .setFirstResult(offset)
               .setMaxResults(limit)
               .getResultList();
    }

    @Override
    public List<Item> findAllOrderByCreatedAtDesc(int offset, int limit) { //최근 item 등록 순으로 정렬
        return em.createQuery("SELECT i FROM Item i ORDER BY i.createdAt DESC", Item.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Item> findAllOrderByCountDesc(int offset, int limit) { //count 개수, 즉 판매량이 많은 item 순으로 정렬
        return em.createQuery("SELECT i FROM Item i ORDER BY i.count DESC", Item.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public List<Item> findByIsSoldout(Boolean isSoldout, int offset, int limit) {
        return em.createQuery("SELECT i FROM Item i WHERE i.isSoldout = :isSoldout", Item.class)
                .setParameter("isSoldout", isSoldout)
                 .setFirstResult(offset)
                 .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Item> findByCategory(String category, int offset, int limit) {
        return em.createQuery("SELECT i FROM Item i WHERE i.category = :category", Item.class)
                .setParameter("category", category)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public void updateItem(Item item) {
        em.merge(item);
    }

    @Override
    @Transactional
    public int updateIsSoldout(Long itemId){
        int result = em.createQuery("UPDATE Item i SET i.isSoldout = 1 - i.isSoldout WHERE i.itemId = :itemId")
                .setParameter("itemId", itemId)
                .executeUpdate();

        return result; //실행된 update query에 의해 영향을 받은 entity의 수
    }

    @Override
    @Transactional
    public int updateStock(Long itemId, int decreasingStock){

        int result = em.createQuery("UPDATE Item i SET i.stock = i.stock - :decreasingStock WHERE i.itemId = :itemId")
                .setParameter("decreasingStock", decreasingStock)
                .setParameter("itemId", itemId)
                .executeUpdate();

        return result;
    }

    @Override
    @Transactional
    public int updateCount(Long itemId, int increasingCount) {

        int result = em.createQuery("UPDATE Item i SET i.count = i.count + :increasingCount WHERE i.itemId = :itemId")
                .setParameter("increasingCount", increasingCount)
                .setParameter("itemId", itemId)
                .executeUpdate();

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
