package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.Order;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaOrderRepository implements OrderRepository {

    private final EntityManager em;

    public JpaOrderRepository(EntityManager em) {
        this.em = em;
    };

    public void save(Order order){
        em.persist(order);
    };

    public Optional<Order> findByOrderId(Long orderId){
        Order order = em.find(Order.class, orderId);
        //조회할 type, 식별자인 pk
        return Optional.ofNullable(order);
    };

    public Optional<List<Order>> findByBuyerId(String buyerId, int offset, int limit){
        List<Order> order = em.createQuery("select i from Order i where i.buyerId = :buyerId", Order.class)
                .setParameter("buyerId", buyerId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return Optional.ofNullable(order);
    };

    public Optional<List<Order>> findBySellerId(String sellerId, int offset, int limit){
        List<Order> order = em.createQuery("select i from Order i where i.sellerId = :sellerId", Order.class)
                .setParameter("sellerId", sellerId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return Optional.ofNullable(order);
    };

    public Optional<List<Order>> findByItemId(Long itemId, int offset, int limit){
        List<Order> order = em.createQuery("select i from Order i where i.itemId = :itemId", Order.class)
                .setParameter("itemId", itemId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return Optional.ofNullable(order);
    };

    public Optional<List<Order>> findAll(int offset, int limit){
        List<Order> order = em.createQuery("select i from Order i", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return Optional.ofNullable(order);
    };

    public void cancel(Long orderId){
        Order order = em.find(Order.class, orderId);
        if (order != null) {
            em.remove(order);
        }
    };
}
