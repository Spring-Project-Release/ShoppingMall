package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.Cart;

import java.util.List;
import java.util.Optional;

//@Repository
public interface CartRepository {

    public void deleteByCartId(Long cartId);
    Cart save(Cart cart);
    Optional<Cart> findByCartId(Long cartId);
    Optional<Cart> findByUserId(Long userId);
    
    //나중에 로그인 정보 확인해야 함
}
