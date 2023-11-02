/*package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.Cart;
import release.release_proj.domain.Item;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaCartRepository {

    private final EntityManager em;

    public JpaCartRepository(EntityManager em) {
        this.em = em;
    }

    Cart save(Cart cart) {
        em.persist(cart);
        return cart;
    }

    public void deleteByCartId(Long cartId) {
        Cart cart = em.find(Cart.class, cartId);
        if (cart != null) {
            em.remove(cart);
        }
    }

    Optional<Cart> findByCartId(Long cartId) {
        Cart cart = em.find(Cart.class, cartId);    //조회할 type, 식별자인 pk

        return Optional.ofNullable(cart);
    }

    Optional<Cart> findByUserId(Long userId) {
        List<Cart> result = em.createQuery("SELECT c FROM Cart c WHERE c.user.userId = :userId", Cart.class)
            .setParameter("userId", userId)
            .getResultList();

        return result.stream().findAny();
    }
}
*/