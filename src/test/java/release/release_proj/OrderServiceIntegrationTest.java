package release.release_proj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Item;
import release.release_proj.domain.MemberVO;
import release.release_proj.dto.OrderRequestDTO;
import release.release_proj.dto.OrderResponseDTO;
import release.release_proj.repository.ItemRepository;
import release.release_proj.repository.MemberDAO;
import release.release_proj.service.OrderService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class OrderServiceIntegrationTest {

    @PersistenceContext private EntityManager em;

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

        Item item = createItem("testItemName", 1, 1, 0, false, member2.getMemberId());
        itemRepository.save(item);

        OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                .buyerId(member1.getMemberId())
                .itemId(item.getItemId())
                .sellerId(item.getSellerId())
                .count(1)
                .price(item.getPrice()*1)
                .build();

        // When
        orderService.save(orderDTO);
        em.flush();
        em.clear();

        // Then
        List<OrderResponseDTO> savedOrderDTO1 = orderService.findByBuyerId(member1.getMemberId(), 1, 20);
        List<OrderResponseDTO> savedOrderDTO2 = orderService.findBySellerId(item.getSellerId(), 1, 20);
        Item savedItem = itemRepository.findByItemId(item.getItemId()).get();
        assertThat(savedOrderDTO1).isNotNull();
        assertThat(savedOrderDTO2).isNotNull();
        assertEquals(savedOrderDTO1.get(0), savedOrderDTO2.get(0));
        assertEquals(savedOrderDTO1.get(0).getOrderId(),savedOrderDTO2.get(0).getOrderId());
        assertThat(savedItem.getStock()).isEqualTo(0);
        assertThat(savedItem.getIsSoldout()).isEqualTo(true);
        assertThat(savedItem.getCount()).isEqualTo(1);
    }

    @Test
    public void 상품결제_외래키_예외_상품_또는_셀러_또는_구매자_없음() throws Exception {
        //Given
        OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                .buyerId("testMemberId1")
                .itemId(99999L)
                .sellerId("testMemberId2")
                .count(1)
                .price(1)
                .build();

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(orderDTO));
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

        OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                .buyerId(member1.getMemberId())
                .itemId(99999L)
                .sellerId("testMemberId2")
                .count(1)
                .price(1)
                .build();

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(orderDTO));
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

        OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                .buyerId(member1.getMemberId())
                .itemId(99999L)
                .sellerId(member2.getMemberId())
                .count(1)
                .price(1)
                .build();

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(orderDTO));
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

        Item item = createItem("testItemName", 1, 1, 0, false, member2.getMemberId());
        itemRepository.save(item);

        OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                .buyerId(member1.getMemberId())
                .itemId(item.getItemId())
                .sellerId(item.getSellerId())
                .count(2)
                .price(item.getPrice() * 2)
                .build();

        // Then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> orderService.save(orderDTO));
        assertThat(e.getMessage()).isEqualTo(orderDTO.getItemId()+" 상품의 재고가 부족합니다.");
    }

    @Test
    public void 상품결제_예외_본인이_판매자_예외() throws Exception {
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

        Item item = createItem("testItemName", 1, 1, 0, false, member1.getMemberId());
        itemRepository.save(item);

        OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                .buyerId(member1.getMemberId())
                .itemId(item.getItemId())
                .sellerId(item.getSellerId())
                .count(2)
                .price(item.getPrice() * 2)
                .build();

        // Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> orderService.save(orderDTO));
        assertThat(e.getMessage()).isEqualTo("본인이 판매중인 상품을 구매할 수 없습니다.");
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

        Item item = createItem("testItemName", 1, 1, 0, false, member1.getMemberId());
        itemRepository.save(item);

        OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                .buyerId(member2.getMemberId())
                .itemId(item.getItemId())
                .sellerId(member1.getMemberId())
                .count(1)
                .price(item.getPrice() * 1)
                .build();

        orderService.save(orderDTO);

        //When
        OrderResponseDTO savedOrderDTO = orderService.findByBuyerId(orderDTO.getBuyerId(), 1, 20).get(0);
        orderService.deleteOrder(savedOrderDTO.getOrderId());

        // Then
        Item savedItem = itemRepository.findByItemId(item.getItemId()).get();
        MemberVO savedMember1 = memberDAO.findMemberById(member1.getMemberId());
        MemberVO savedMember2 = memberDAO.findMemberById(member2.getMemberId());

        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> orderService.findByBuyerId(savedMember2.getMemberId(), 1, 20));
        assertThat(e1.getMessage()).isEqualTo("해당 buyerId를 가지는 구매내역이 존재하지 않습니다.");
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> orderService.findBySellerId(savedMember1.getMemberId(), 1, 20));
        assertThat(e2.getMessage()).isEqualTo("해당 sellerId를 가지는 판매내역이 존재하지 않습니다.");
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> orderService.findByItemId(savedItem.getItemId(), 1, 20));
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

        Item item = createItem("testItemName", 1, 1, 0, false, member1.getMemberId());
        itemRepository.save(item);

        OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                .buyerId(member2.getMemberId())
                .itemId(item.getItemId())
                .sellerId(member1.getMemberId())
                .count(1)
                .price(item.getPrice() * 1)
                .build();

        orderService.save(orderDTO);

        // Then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> orderService.deleteOrder(99999L));
        assertThat(e.getMessage()).isEqualTo("해당 주문 ID: " + 99999 + "가 존재하지 않습니다.");
    }

    private Item createItem(String name, int stock, int price, int count, Boolean isSoldout, String sellerId) {
        Item item = Item.builder()
                .name(name)
                .stock(stock)
                .price(price)
                .count(count)
                .isSoldout(isSoldout)
                .sellerId(sellerId)
                .build();

        em.persist(item);
        return item;
    }
}