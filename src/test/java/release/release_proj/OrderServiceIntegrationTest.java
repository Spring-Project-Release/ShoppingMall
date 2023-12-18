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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class OrderServiceIntegrationTest {

    @Autowired OrderService orderService;
    @Autowired MemberDAO memberDAO;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품결제와_상품품절_업데이트() throws Exception { //주문하면 품절되어 item이 soldout되는 것까지 테스트
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
        item.setStock(1);
        item.setCount(0);
        item.setIsSoldout(false);
        itemRepository.save(item);

        Order order = new Order();
        order.setMemberId("testMemberId");
        order.setItemId(item.getItemId());
        order.setCount(1);
        order.setPrice(order.getCount()*item.getPrice());

        // When
        orderService.save(order);

        // Then
        Optional<List<Order>> savedOrder = orderService.findByMemberId("testMemberId");
        assertThat(savedOrder).isPresent();
        assertEquals(order.getOrderId(),savedOrder.get().get(0).getOrderId());
        Item savedItem = itemRepository.findByItemId(item.getItemId()).get();
        assertThat(savedItem.getStock()).isEqualTo(0);
        assertThat(savedItem.getIsSoldout()).isEqualTo(true);
        assertThat(savedItem.getCount()).isEqualTo(1);
    }

    @Test
    public void 상품결제_외래키_예외_상품과유저_없음() throws Exception {
        //Given
        Order order = new Order();
        order.setMemberId("testMemberId");
        order.setItemId(99999L);
        order.setCount(1);
        order.setPrice(1);

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertThat(e.getMessage()).isEqualTo("결제 처리에 실패했습니다. 해당하는 memberId와 itemId가 존재하지 않습니다.");
    }

    @Test
    public void 상품결제_외래키_예외_상품_없음() throws Exception {
        //Given
        Item item = new Item();
        item.setName("testItemName");
        item.setPrice(1);
        item.setStock(1);
        item.setCount(0);
        item.setIsSoldout(false);
        itemRepository.save(item);

        Order order = new Order();
        order.setMemberId("testMemberId");
        order.setItemId(item.getItemId());
        order.setCount(1);
        order.setPrice(1);

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertThat(e.getMessage()).isEqualTo("결제 처리에 실패했습니다. 해당하는 memberId가 존재하지 않습니다.");
    }

    @Test
    public void 상품결제_외래키_예외_유저_없음() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        Order order = new Order();
        order.setMemberId("testMemberId");
        order.setItemId(99999L);
        order.setCount(1);
        order.setPrice(1);

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertThat(e.getMessage()).isEqualTo("결제 처리에 실패했습니다. 해당하는 itemId가 존재하지 않습니다.");
    }

    @Test
    public void 상품결제_예외_상품재고부족() throws Exception {
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
        item.setStock(1);
        item.setCount(0);
        item.setIsSoldout(false);
        itemRepository.save(item);

        Order order = new Order();
        order.setMemberId("testMemberId");
        order.setItemId(item.getItemId());
        order.setCount(2);
        order.setPrice(order.getCount()*item.getPrice());

        // Then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> orderService.save(order));
        assertThat(e.getMessage()).isEqualTo(order.getItemId()+" 상품의 재고가 부족합니다.");
    }
}
