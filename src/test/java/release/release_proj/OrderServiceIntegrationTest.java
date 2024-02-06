package release.release_proj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Item;
import release.release_proj.domain.MemberVO;
import release.release_proj.domain.Order;
import release.release_proj.repository.ItemRepository;
import release.release_proj.repository.MemberDAO;
import release.release_proj.service.OrderService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
/*
@SpringBootTest
@Transactional
public class OrderServiceIntegrationTest {

    @Autowired OrderService orderService;
    @Autowired MemberDAO memberDAO;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품결제와_상품품절_업데이트() throws Exception { //주문하면 품절되어 item이 soldout되는 것까지 테스트
        // Given
        MemberVO member1 = new MemberVO();
        member1.setMemberId("testMemberId1");
        member1.setMemberName("testMemberName1");
        member1.setMemberPassword("testMemberPassword1");
        member1.setMemberPhone("testMemberPhone1");
        member1.setMemberAddress("testMemberAddress1");
        member1.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member1);

        MemberVO member2 = new MemberVO();
        member2.setMemberId("testMemberId2");
        member2.setMemberName("testMemberName2");
        member2.setMemberPassword("testMemberPassword2");
        member2.setMemberPhone("testMemberPhone2");
        member2.setMemberAddress("testMemberAddress2");
        member2.setMemberEmail("testMemberEmail2");
        memberDAO.insertMember(member2);

        Item item = new Item();
        item.setName("testItemName");
        item.setPrice(1);
        item.setStock(1);
        item.setCount(0);
        item.setIsSoldout(false);
        item.setSellerId(member2.getMemberId());
        itemRepository.save(item);

        Order order = new Order();
        order.setBuyerId(member1.getMemberId());
        order.setItemId(item.getItemId());
        order.setSellerId(item.getSellerId());
        order.setCount(1);
        order.setPrice(order.getCount()*item.getPrice());

        // When
        orderService.save(order);

        // Then
        List<Order> savedOrder1 = orderService.findByBuyerId(member1.getMemberId());
        List<Order> savedOrder2 = orderService.findBySellerId(item.getSellerId());
        Item savedItem = itemRepository.findByItemId(item.getItemId()).get();
        assertThat(savedOrder1).isNotNull();
        assertThat(savedOrder2).isNotNull();
        assertEquals(savedOrder1.get(0), savedOrder2.get(0));
        assertEquals(order.getOrderId(),savedOrder1.get(0).getOrderId());
        assertThat(savedItem.getStock()).isEqualTo(0);
        assertThat(savedItem.getIsSoldout()).isEqualTo(true);
        assertThat(savedItem.getCount()).isEqualTo(1);
    }

    @Test
    public void 상품결제_외래키_예외_상품_또는_셀러_또는_구매자_없음() throws Exception {
        //Given
        Order order = new Order();
        order.setBuyerId("testMemberId1");
        order.setSellerId("testMemberId2");
        order.setItemId(99999L);
        order.setCount(1);
        order.setPrice(1);

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertThat(e.getMessage()).isEqualTo("결제 처리에 실패했습니다. 해당하는 buyerId가 존재하지 않습니다.");
    }

    @Test
    public void 상품결제_외래키_예외_상품_또는_판매자_없음() throws Exception {
        //Given
        MemberVO member1 = new MemberVO();
        member1.setMemberId("testMemberId1");
        member1.setMemberName("testMemberName1");
        member1.setMemberPassword("testMemberPassword1");
        member1.setMemberPhone("testMemberPhone1");
        member1.setMemberAddress("testMemberAddress1");
        member1.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member1);

        Order order = new Order();
        order.setItemId(999999L);
        order.setBuyerId(member1.getMemberId());
        order.setSellerId("testMemberId2");
        order.setCount(1);
        order.setPrice(1);

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertThat(e.getMessage()).isEqualTo("결제 처리에 실패했습니다. 해당하는 sellerId가 존재하지 않습니다.");
    }

    @Test
    public void 상품결제_외래키_예외_상품_없음() throws Exception {
        //Given
        MemberVO member1 = new MemberVO();
        member1.setMemberId("testMemberId1");
        member1.setMemberName("testMemberName1");
        member1.setMemberPassword("testMemberPassword1");
        member1.setMemberPhone("testMemberPhone1");
        member1.setMemberAddress("testMemberAddress1");
        member1.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member1);

        MemberVO member2 = new MemberVO();
        member2.setMemberId("testMemberId2");
        member2.setMemberName("testMemberName2");
        member2.setMemberPassword("testMemberPassword2");
        member2.setMemberPhone("testMemberPhone2");
        member2.setMemberAddress("testMemberAddress2");
        member2.setMemberEmail("testMemberEmail2");
        memberDAO.insertMember(member2);

        Order order = new Order();
        order.setBuyerId(member1.getMemberId());
        order.setSellerId(member2.getMemberId());
        order.setItemId(999999L);
        order.setCount(1);
        order.setPrice(1);

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertThat(e.getMessage()).isEqualTo("결제 처리에 실패했습니다. 해당하는 itemId가 존재하지 않습니다.");
    }

    @Test
    public void 상품결제_예외_상품재고부족() throws Exception {
        // Given
        MemberVO member1 = new MemberVO();
        member1.setMemberId("testMemberId1");
        member1.setMemberName("testMemberName1");
        member1.setMemberPassword("testMemberPassword1");
        member1.setMemberPhone("testMemberPhone1");
        member1.setMemberAddress("testMemberAddress1");
        member1.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member1);

        MemberVO member2 = new MemberVO();
        member2.setMemberId("testMemberId2");
        member2.setMemberName("testMemberName2");
        member2.setMemberPassword("testMemberPassword2");
        member2.setMemberPhone("testMemberPhone2");
        member2.setMemberAddress("testMemberAddress2");
        member2.setMemberEmail("testMemberEmail2");
        memberDAO.insertMember(member2);

        Item item = new Item();
        item.setName("testItemName");
        item.setPrice(1);
        item.setStock(1);
        item.setCount(0);
        item.setIsSoldout(false);
        item.setSellerId(member2.getMemberId());
        itemRepository.save(item);

        Order order = new Order();
        order.setBuyerId(member1.getMemberId());
        order.setSellerId(item.getSellerId());
        order.setItemId(item.getItemId());
        order.setCount(2);
        order.setPrice(order.getCount()*item.getPrice());

        // Then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> orderService.save(order));
        assertThat(e.getMessage()).isEqualTo(order.getItemId()+" 상품의 재고가 부족합니다.");
    }

    @Test
    public void 주문삭제() throws Exception { //재고가 품절되었는데 주문삭제로 품절취소되는 경우까지 테스트
        // Given
        MemberVO member1 = new MemberVO();
        member1.setMemberId("testMemberId1");
        member1.setMemberName("testMemberName1");
        member1.setMemberPassword("testMemberPassword1");
        member1.setMemberPhone("testMemberPhone1");
        member1.setMemberAddress("testMemberAddress1");
        member1.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member1);

        MemberVO member2 = new MemberVO();
        member2.setMemberId("testMemberId2");
        member2.setMemberName("testMemberName2");
        member2.setMemberPassword("testMemberPassword2");
        member2.setMemberPhone("testMemberPhone2");
        member2.setMemberAddress("testMemberAddress2");
        member2.setMemberEmail("testMemberEmail2");
        memberDAO.insertMember(member2);

        Item item = new Item();
        item.setName("testItemName");
        item.setPrice(1);
        item.setStock(1);
        item.setCount(0);
        item.setIsSoldout(false);
        item.setSellerId(member1.getMemberId());
        itemRepository.save(item);

        Order order = new Order();
        order.setBuyerId(member2.getMemberId());
        order.setSellerId(member1.getMemberId());
        order.setItemId(item.getItemId());
        order.setCount(1);
        order.setPrice(order.getCount()*item.getPrice());

        orderService.save(order);

        //When
        orderService.deleteOrder(order.getOrderId());

        // Then
        Item savedItem = itemRepository.findByItemId(item.getItemId()).get();
        MemberVO savedMember1 = memberDAO.findMemberById(member1.getMemberId());
        MemberVO savedMember2 = memberDAO.findMemberById(member2.getMemberId());
        ////////////////////////
        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> orderService.findByBuyerId(savedMember2.getMemberId()));
        assertThat(e1.getMessage()).isEqualTo("해당 buyerId를 가지는 구매내역이 존재하지 않습니다.");
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> orderService.findBySellerId(savedMember1.getMemberId()));
        assertThat(e2.getMessage()).isEqualTo("해당 sellerId를 가지는 판매내역이 존재하지 않습니다.");
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> orderService.findByItemId(savedItem.getItemId()));
        assertThat(e3.getMessage()).isEqualTo("해당 itemId를 가지는 주문내역이 존재하지 않습니다.");
        assertThat(savedItem.getStock()).isEqualTo(1);
        assertThat(savedItem.getIsSoldout()).isEqualTo(false);
        assertThat(savedItem.getCount()).isEqualTo(0);
    }

    @Test
    public void 주문삭제_예외() throws Exception { //존재하지 않는 orderId 이용하는 경우
        // Given
        MemberVO member1 = new MemberVO();
        member1.setMemberId("testMemberId1");
        member1.setMemberName("testMemberName1");
        member1.setMemberPassword("testMemberPassword1");
        member1.setMemberPhone("testMemberPhone1");
        member1.setMemberAddress("testMemberAddress1");
        member1.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member1);

        MemberVO member2 = new MemberVO();
        member2.setMemberId("testMemberId2");
        member2.setMemberName("testMemberName2");
        member2.setMemberPassword("testMemberPassword2");
        member2.setMemberPhone("testMemberPhone2");
        member2.setMemberAddress("testMemberAddress2");
        member2.setMemberEmail("testMemberEmail2");
        memberDAO.insertMember(member2);

        Item item = new Item();
        item.setName("testItemName");
        item.setPrice(1);
        item.setStock(1);
        item.setCount(0);
        item.setIsSoldout(false);
        item.setSellerId(member1.getMemberId());
        itemRepository.save(item);

        Order order = new Order();
        order.setBuyerId(member2.getMemberId());
        order.setSellerId(member1.getMemberId());
        order.setItemId(item.getItemId());
        order.setCount(1);
        order.setPrice(order.getCount()*item.getPrice());

        orderService.save(order);

        // Then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> orderService.deleteOrder(99999L));
        assertThat(e.getMessage()).isEqualTo("해당 주문 ID: " + 99999 + "가 존재하지 않습니다.");
    }
}
*/