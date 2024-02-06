package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.Cart;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaCartRepository implements CartRepository {

    private final EntityManager em;

    public JpaCartRepository(EntityManager em) {
        this.em = em;
    };

    @Override
    public int deleteByMemberId(String memberId) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        int result = em.createQuery("DELETE FROM Cart c WHERE c.memberId = :memberId")
                .setParameter("memberId", memberId)
                .executeUpdate();

        transaction.commit();
    }

    @Override
    public int deleteByMemberIdAndItemId(String memberId, Long itemId) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        int result = em.createQuery("DELETE FROM Cart c WHERE c.memberId = :memberId AND c.itemId = :itemId")
                .setParameter("memberId", memberId)
                .setParameter("itemId", itemId)
                .executeUpdate();

        transaction.commit();
    }

    @Override
    public void save(Cart cart) {
        em.persist(cart);
    }

    @Override
    public Optional<List<Cart>> findByMemberId(String memberId) {
        List<Cart> carts = em.createQuery("SELECT c FROM Cart c WHERE c.memberId = :memberId", Cart.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return Optional.ofNullable(carts);
    }

    @Override
    public Optional<Cart> findByMemberIdAndItemId(String memberId, Long itemId) {
       Cart cart = em.createQuery("SELECT c FROM Cart c WHERE c.memberId = :memberId AND c.itemId = :itemId", Cart.class)
                    .setParameter("memberId", memberId)
                    .setParameter("itemId", itemId)
                    .getSingleResult());

      return Optional.ofNullable(cart);
    }

    @Override
    public int updateCartAmount(Long cartId, int amount) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        int result = em.createQuery("UPDATE Cart c SET c.amount = :amount WHERE c.cartId = :cartId")
            .setParameter("amount", amount)
            .setParameter("cartId", cartId)
            .executeUpdate();

       transaction.commit();

        return result;
    }

    @Override
    public int deleteCartIfAmountIsZero(Long cartId){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        int result = em.createQuery("DELETE FROM Cart c WHERE c.cartId = :cartId AND c.amount = 0")
            .setParameter("cartId", cartId)
            .executeUpdate();

        transaction.commit();

        return result;
    }
}
