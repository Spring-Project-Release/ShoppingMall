package release.release_proj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Cart;
import release.release_proj.domain.Item;
import release.release_proj.domain.MemberVO;
import release.release_proj.repository.ItemRepository;
import release.release_proj.repository.MemberDAO;
import release.release_proj.service.CartService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CartServiceIntegrationTest {

    @Autowired CartService cartService;
    @Autowired ItemRepository itemRepository;
    @Autowired MemberDAO memberDAO;

    @Test
    public void 장바구니추가() throws Exception {
        // Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setName("testMemberName");
        member.setPassword("testMemberPassword");
        member.setPhone("testMemberPhone");
        member.setAddress("testMemberAddress");
        member.setEmail("testMemberEmail");
        memberDAO.insertMember(member);

        Item item = new Item();
        item.setItemId(1L);
        item.setName("testItemName");
        item.setPrice(1);
        item.setIsSoldout(true);
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMemberId("testMemberId");
        cart.setItemId(1L);
        cart.setAmount(2);

        // When
        int result = cartService.addCartItem(cart);

        // Then
        Optional<List<Cart>> carts = cartService.readMemberCartItems("testMemberId", 1L);
        assertThat(carts).isPresent();
        assertEquals(cart.getCartId(), carts.get().get(0).getCartId());
    }

    @Test
    public void 장바구니추가_외래키_예외() throws Exception {
        Cart cart = new Cart();
        cart.setMemberId("testMemberId");
        cart.setItemId(1L);
        cart.setAmount(2);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cartService.addCartItem(cart));
        assertThat(e.getMessage()).isEqualTo("Failed to create cart. 해당하는 itemId나 memberId가 존재하지 않습니다");

    }
}
