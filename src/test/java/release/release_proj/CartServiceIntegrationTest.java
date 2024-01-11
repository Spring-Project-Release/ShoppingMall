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
import release.release_proj.repository.OrderRepository;
import release.release_proj.service.CartService;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired OrderRepository orderRepository;
    @Autowired MemberDAO memberDAO;

    @Test
    public void 장바구니추가_이전에_존재하지않음() throws Exception {
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
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId("testSellerId");
        item.setSellerName("testSellerName");
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMemberId(member.getMemberId());
        cart.setItemId(item.getItemId());
        cart.setAmount(2);

        // When
        int result = cartService.addCartItem(cart);

        // Then
        Optional<Cart> savedCart = cartService.readMemberCartItems(member.getMemberId(), item.getItemId());
        assertThat(savedCart).isPresent();
        assertEquals(cart.getCartId(), savedCart.get().getCartId());
    }

    @Test
    public void 장바구니추가_이전에_존재() throws Exception {
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
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId("testSellerId");
        item.setSellerName("testSellerName");
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMemberId(member.getMemberId());
        cart.setItemId(item.getItemId());
        cart.setAmount(2);

        Cart newCart = new Cart();
        newCart.setMemberId(member.getMemberId());
        newCart.setItemId(item.getItemId());
        newCart.setAmount(1);

        // When
        int result1 = cartService.addCartItem(cart);
        int result2 = cartService.addCartItem(newCart);

        // Then
        Optional<Cart> savedCart = cartService.readMemberCartItems(member.getMemberId(), item.getItemId());
        assertThat(savedCart).isPresent();
        assertThat(savedCart.get().getAmount()).isEqualTo(3);
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

    @Test
    public void 유저_장바구니_삭제() throws Exception {
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
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId("testSellerId");
        item.setSellerName("testSellerName");
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMemberId(member.getMemberId());
        cart.setItemId(item.getItemId());
        cart.setAmount(2);

        cartService.addCartItem(cart);

        // When
        int result = cartService.deleteCart(member.getMemberId());

        // Then
       assertThat(result).isGreaterThan(0);
       assertThat(cartService.readMemberCarts(member.getMemberId()).get()).isEqualTo(Collections.emptyList());
    }

    @Test
    public void 유저_장바구니_삭제_예외() throws Exception { //해당 장바구니가 존재하지 않을 경우
        // Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        // When
        int result = cartService.deleteCart(member.getMemberId());

        // Then
        assertThat(result).isEqualTo(0);
        assertThat(cartService.readMemberCarts(member.getMemberId()).get()).isEqualTo(Collections.emptyList());
    }

    @Test
    public void 유저_장바구니_특정상품_삭제() throws Exception {
        // Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        Item item1 = new Item();
        item1.setName("testItemName");
        item1.setStock(1);
        item1.setPrice(1);
        item1.setIsSoldout(false);
        item1.setSellerId("testSellerId");
        item1.setSellerName("testSellerName");
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("testItemName");
        item2.setStock(1);
        item2.setPrice(1);
        item2.setIsSoldout(false);
        item2.setSellerId("testSellerId");
        item2.setSellerName("testSellerName");
        itemRepository.save(item2);

        Cart cart1 = new Cart();
        cart1.setMemberId(member.getMemberId());
        cart1.setItemId(item1.getItemId());
        cart1.setAmount(2);

        Cart cart2 = new Cart();
        cart2.setMemberId(member.getMemberId());
        cart2.setItemId(item2.getItemId());
        cart2.setAmount(1);

        cartService.addCartItem(cart1);
        cartService.addCartItem(cart2);

        // When
        int result = cartService.deleteCartItem(member.getMemberId(), item1.getItemId());

        // Then
        assertThat(result).isGreaterThan(0);
        assertThat(cartService.readMemberCartItems(member.getMemberId(), item2.getItemId())).isPresent();
        assertThat(cartService.readMemberCarts(member.getMemberId())).isPresent();
        assertThat(cartService.readMemberCartItems(member.getMemberId(), item1.getItemId())).isEmpty();
    }

    @Test
    public void 유저_장바구니_특정상품_삭제_예외() throws Exception {
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
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId("testSellerId");
        item.setSellerName("testSellerName");
        itemRepository.save(item);

        // When
        int result = cartService.deleteCartItem(member.getMemberId(), item.getItemId()); //존재하지 않는 장바구니 물품 삭제

        // Then
        assertThat(result).isEqualTo(0);
        assertThat(cartService.readMemberCartItems(member.getMemberId(), item.getItemId())).isEmpty();
    }

    @Test
    public void 장바구니_상품개수_감소() throws Exception { //상품개수 감소로 삭제까지
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
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId("testSellerId");
        item.setSellerName("testSellerName");
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMemberId(member.getMemberId());
        cart.setItemId(item.getItemId());
        cart.setAmount(2);
        cartService.addCartItem(cart);

        Long itemId = item.getItemId();
        String memberId = member.getMemberId();

        // When
        cartService.decreaseCartItem(cart.getCartId());
        Optional<Cart> savedCart1 = cartService.readMemberCartItems(memberId, itemId);
        cartService.decreaseCartItem(cart.getCartId());
        Optional<Cart> savedCart2 = cartService.readMemberCartItems(memberId, itemId);

        // Then
        assertThat(savedCart1).isPresent();
        assertThat(savedCart1.get().getAmount()).isEqualTo(1);
        assertThat(savedCart2).isEmpty();
    }

    @Test
    public void 장바구니_상품개수_증가() throws Exception { //상품개수 감소로 삭제까지
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
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId("testSellerId");
        item.setSellerName("testSellerName");
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMemberId(member.getMemberId());
        cart.setItemId(item.getItemId());
        cart.setAmount(2);
        cartService.addCartItem(cart);

        // When
        cartService.increaseCartItem(cart.getCartId(), 2);
        Optional<Cart> savedCart1 = cartService.readMemberCartItems(member.getMemberId(), item.getItemId());

        // Then
        assertThat(savedCart1).isPresent();
        assertThat(savedCart1.get().getAmount()).isEqualTo(4);
    }

    @Test
    public void 유저_장바구니_전체_결제() throws Exception {
        // Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        Item item1 = new Item();
        item1.setName("testItemName");
        item1.setStock(1);
        item1.setPrice(1);
        item1.setIsSoldout(false);
        item1.setSellerId("testSellerId");
        item1.setSellerName("testSellerName");
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("testItemName");
        item2.setStock(1);
        item2.setPrice(1);
        item2.setIsSoldout(false);
        item2.setSellerId("testSellerId");
        item2.setSellerName("testSellerName");
        itemRepository.save(item2);

        String memberId = member.getMemberId();
        Cart cart1 = new Cart();
        cart1.setMemberId(memberId);
        cart1.setItemId(item1.getItemId());
        cart1.setAmount(1);

        Cart cart2 = new Cart();
        cart2.setMemberId(memberId);
        cart2.setItemId(item2.getItemId());
        cart2.setAmount(1);

        cartService.addCartItem(cart1);
        cartService.addCartItem(cart2);

        // When
        cartService.payAllCart(memberId, "전체결제");

        // Then
        assertThat(orderRepository.findByMemberId(memberId)).isPresent();
        assertEquals(orderRepository.findByMemberId(memberId).get().size(), 2);
        assertThat(orderRepository.findByMemberId(memberId).get().get(0).getItemId()).isEqualTo(item1.getItemId());
        assertThat(orderRepository.findByMemberId(memberId).get().get(1).getItemId()).isEqualTo(item2.getItemId());
        assertThat(orderRepository.findByMemberId(memberId).get().get(0).getMemo()).isEqualTo("전체결제");
        assertThat(orderRepository.findByMemberId(memberId).get().get(1).getMemo()).isEqualTo("전체결제");
        assertThat(cartService.readMemberCarts(memberId)).get().isEqualTo(Collections.emptyList()); //유저의 장바구니 전체가 삭제됨
    }

    @Test
    public void 유저_장바구니_전체_결제_예외_유저존재하지않음() throws Exception {

        // Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cartService.payAllCart("testMemberId", "전체결제"));
        assertThat(e.getMessage()).isEqualTo("Invalid memberId: " + "testMemberId");
    }

    @Test
    public void 유저_장바구니_전체_결제_예외_cartDB안_없는유저() throws Exception {
        // Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        // Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cartService.payAllCart(member.getMemberId(), "전체결제"));
        assertThat(e.getMessage()).isEqualTo("해당하는 memberId: "+member.getMemberId()+"를 갖는 cart가 존재하지 않습니다.");
    }

    @Test
    public void 유저_장바구니_일부_결제() throws Exception {
        // Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        Item item1 = new Item();
        item1.setName("testItemName");
        item1.setStock(1);
        item1.setPrice(1);
        item1.setIsSoldout(false);
        item1.setSellerId("testSellerId");
        item1.setSellerName("testSellerName");
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("testItemName");
        item2.setStock(1);
        item2.setPrice(1);
        item2.setIsSoldout(false);
        item2.setSellerId("testSellerId");
        item2.setSellerName("testSellerName");
        itemRepository.save(item2);

        String memberId = member.getMemberId();
        Cart cart1 = new Cart();
        cart1.setMemberId(memberId);
        cart1.setItemId(item1.getItemId());
        cart1.setAmount(1);

        Cart cart2 = new Cart();
        cart2.setMemberId(memberId);
        cart2.setItemId(item2.getItemId());
        cart2.setAmount(1);

        cartService.addCartItem(cart1);
        cartService.addCartItem(cart2);

        // When
        List<Long> tempList = new ArrayList<>();
        tempList.add(item1.getItemId());
        cartService.paySomeCart(memberId, tempList, "일부결제");

        // Then
        assertThat(orderRepository.findByMemberId(memberId)).isPresent();
        assertEquals(orderRepository.findByMemberId(memberId).get().size(), 1);
        assertThat(orderRepository.findByMemberId(memberId).get().get(0).getItemId()).isEqualTo(item1.getItemId());
        assertThat(orderRepository.findByMemberId(memberId).get().get(0).getMemo()).isEqualTo("일부결제");
        assertThat(cartService.readMemberCarts(memberId).get().get(0).getItemId()).isEqualTo(item2.getItemId()); //유저의 장바구니 전체가 삭제됨
    }

    @Test
    public void 유저_장바구니_일부_결제_예외_유저존재하지않음() throws Exception {
        //Given
        List<Long> tempList = new ArrayList<>();
        tempList.add(9999L);

        // Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cartService.paySomeCart("testMemberId", tempList, "일부결제"));
        assertThat(e.getMessage()).isEqualTo("Invalid memberId: " + "testMemberId");
    }

    @Test
    public void 유저_장바구니_일부_결제_예외_상품존재하지않음() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        List<Long> tempList = new ArrayList<>();
        tempList.add(9999L);

        // Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cartService.paySomeCart("testMemberId", tempList, "일부결제"));
        assertThat(e.getMessage()).isEqualTo("Invalid itemId: " + 9999L);
    }

    @Test
    public void 유저_장바구니_일부_결제_예외_cartDB안_없는상품() throws Exception {
        //Given
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
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId("testSellerId");
        item.setSellerName("testSellerName");
        itemRepository.save(item);

        List<Long> tempList = new ArrayList<>();
        tempList.add(item.getItemId());

        // Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cartService.paySomeCart("testMemberId", tempList, "일부결제"));
        assertThat(e.getMessage()).isEqualTo("해당하는 itemId: " + item.getItemId() + "와 memberId: " + member.getMemberId() + "를 갖는 cart가 존재하지 않습니다.");
    }
}
