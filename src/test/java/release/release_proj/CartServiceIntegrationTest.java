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
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        Item item = new Item();
        item.setName("testItemName");
        item.setPrice(1);
        item.setIsSoldout(false);
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMemberId("testMemberId");
        cart.setItemId(item.getItemId());
        cart.setAmount(2);

        // When
        int result = cartService.addCartItem(cart);

        // Then
        Optional<Cart> savedCart = cartService.readMemberCartItems("testMemberId", item.getItemId());
        assertThat(savedCart).isPresent();
        assertEquals(cart.getCartId(), savedCart.get().getCartId());
    }

    @Test
    public void 장바구니추가_외래키_예외() throws Exception {
        Cart cart = new Cart();
        cart.setMemberId("testMemberId");
        cart.setItemId(99999L);
        cart.setAmount(2);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cartService.addCartItem(cart));
        assertThat(e.getMessage()).isEqualTo("Failed to create cart. 해당하는 itemId나 memberId가 존재하지 않습니다");

    }
}
