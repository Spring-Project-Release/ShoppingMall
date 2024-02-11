package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Cart;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaCartRepository implements CartRepository {

    private final EntityManager em;

    public JpaCartRepository(EntityManager em) {
        this.em = em;
    };

    @Override
    @Transactional
    public int deleteByMemberId(String memberId) {

        int result = em.createQuery("DELETE FROM Cart c WHERE c.memberId = :memberId")
                .setParameter("memberId", memberId)
                .executeUpdate();

        return result;
    }

    @Override
    @Transactional
    public int deleteByMemberIdAndItemId(String memberId, Long itemId) {

        int result = em.createQuery("DELETE FROM Cart c WHERE c.memberId = :memberId AND c.itemId = :itemId")
                .setParameter("memberId", memberId)
                .setParameter("itemId", itemId)
                .executeUpdate();

        return result;
    }

    @Override
    public void save(Cart cart) {
        em.persist(cart);
    }

    @Override
    public Optional<List<Cart>> findByMemberId(String memberId, int offset, int limit) {
        List<Cart> carts = em.createQuery("SELECT c FROM Cart c WHERE c.memberId = :memberId", Cart.class)
                .setParameter("memberId", memberId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return Optional.ofNullable(carts);
    }

    @Override
    public Optional<Cart> findByMemberIdAndItemId(String memberId, Long itemId) {
       List<Cart> carts = em.createQuery("SELECT c FROM Cart c WHERE c.memberId = :memberId AND c.itemId = :itemId", Cart.class)
                    .setParameter("memberId", memberId)
                    .setParameter("itemId", itemId)
                    .getResultList();

       if (carts.isEmpty()) {
            return Optional.ofNullable(null);
        } else {
            return Optional.ofNullable(carts.get(0));
        }
    }

    @Override
    @Transactional
    public int updateCartAmount(Long cartId, int amount) {

        int result = em.createQuery("UPDATE Cart c SET c.amount = c.amount + :amount WHERE c.cartId = :cartId")
            .setParameter("amount", amount)
            .setParameter("cartId", cartId)
            .executeUpdate();

        return result;
    }

    @Override
    @Transactional
    public int deleteCartIfAmountIsZero(Long cartId){

        int result = em.createQuery("DELETE FROM Cart c WHERE c.cartId = :cartId AND c.amount = 0")
            .setParameter("cartId", cartId)
            .executeUpdate();

        return result;
    }
}
