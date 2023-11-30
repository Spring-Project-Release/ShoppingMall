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

    public Optional<List<Order>> findByMemberId(String memberId){
        List<Order> order = em.createQuery("select i from Order i where i.memberId = :", Order.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return Optional.ofNullable(order);
    };

    public Optional<List<Order>> findByItemId(Long itemId){
        List<Order> order = em.createQuery("select i from Order i where i.itemId = :", Order.class)
                .setParameter("itemId", itemId)
                .getResultList();

        return Optional.ofNullable(order);
    };

    public Optional<List<Order>> findAll(){
        List<Order> order = em.createQuery("select i from Item i", Order.class)
                .getResultList();

        return Optional.ofNullable(order);
    };

    public void cancelOrder(Long orderId){
        
    };
}
