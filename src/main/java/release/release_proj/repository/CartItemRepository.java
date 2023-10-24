package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository {

    public void deleteByCartItemId(Long cartItemId);
    Optional<CartItem> findByCartItemId(Long cartItemId);
    List<CartItem> findAll();
    CartItem save(CartItem cartItem);

    //나중에 로그인 정보 확인해야 함
}
