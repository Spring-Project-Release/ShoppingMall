package release.release_proj.repository;

import release.release_proj.domain.CartItem;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaCartItemRepository {

    private final EntityManager em;

    public JpaCartItemRepository(EntityManager em) {
        this.em = em;
    }

    public void deleteByCartItemId(Long cartItemId){
        CartItem cartItem = em.find(CartItem.class, cartItemId);
        if (cartItem != null) {
            em.remove(cartItem);
        }
    }

    CartItem save(CartItem cartItem) {
        em.persist(cartItem);
        return cartItem;
    }

    Optional<CartItem> findByCartItemId(Long cartItemId) {
        CartItem cartItem = em.find(CartItem.class, cartItemId);    //조회할 type, 식별자인 pk

        return Optional.ofNullable(cartItem);
    }

    Optional<CartItem> findByCartIdAndItemId(Long cartId, Long itemId){
        List<CartItem> result = em.createQuery("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = :cartId AND ci.item.itemId = :itemId", CartItem.class)
             .setParameter("cartId", cartId)
                .setParameter("itemId", itemId)
                .getResultList();

        return result.stream().findAny();
    }

    List<CartItem> findAll() {
        return em.createQuery("select i from CartItem i", CartItem.class)
                .getResultList();
        // jpql: table이 아니라 객체(entity)를 대상으로 query를 날리는 것 => 이게 sql로 번역이 됨
        // 여기서는 객체 entity인 i 자체를 select 함
    }
}
