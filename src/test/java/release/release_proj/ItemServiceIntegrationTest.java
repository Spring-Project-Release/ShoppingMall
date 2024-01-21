package release.release_proj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Item;
import release.release_proj.domain.MemberVO;
import release.release_proj.repository.ItemRepository;
import release.release_proj.repository.MemberDAO;
import release.release_proj.service.ItemService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemServiceIntegrationTest {

    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;
    @Autowired MemberDAO memberDAO;

    @Test
    public void 상품등록() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId1");
        member.setMemberName("testMemberName1");
        member.setMemberPassword("testMemberPassword1");
        member.setMemberPhone("testMemberPhone1");
        member.setMemberAddress("testMemberAddress1");
        member.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member);

        Item item = new Item();
        item.setName("testItemId");
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId(member.getMemberId());

        //When
        int result = itemService.saveItem(item);

        //Then
        Item savedItem = itemService.findOne(item.getItemId());
        assertThat(savedItem).isNotNull();
        assertThat(item.getName()).isEqualTo(savedItem.getName());
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void 상품등록_외래키_예외_판매자_없음() throws Exception {
        //Given
        Item item = new Item();
        item.setName("testItemId");
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId("testSellerId");

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->itemService.saveItem(item)); //예외가 발생해야 함
        assertThat(e.getMessage()).isEqualTo("해당하는 상품의 sellerId가 유저 DB에 존재하지 않습니다.");
    }

    @Test
    public void 상품등록_예외_상품이름_중복() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId1");
        member.setMemberName("testMemberName1");
        member.setMemberPassword("testMemberPassword1");
        member.setMemberPhone("testMemberPhone1");
        member.setMemberAddress("testMemberAddress1");
        member.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member);

        Item item1 = new Item();
        item1.setName("testItemId");
        item1.setStock(1);
        item1.setPrice(1);
        item1.setIsSoldout(false);
        item1.setSellerId(member.getMemberId());

        Item item2 = new Item();
        item2.setName("testItemId");
        item2.setStock(1);
        item2.setPrice(1);
        item2.setIsSoldout(false);
        item2.setSellerId(member.getMemberId());

        //When
        int result = itemService.saveItem(item1);

        //Then
        IllegalStateException e = assertThrows(IllegalStateException.class, ()->itemService.saveItem(item2)); //예외가 발생해야 함
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
    }

    @Test
    public void 상품_업데이트() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId1");
        member.setMemberName("testMemberName1");
        member.setMemberPassword("testMemberPassword1");
        member.setMemberPhone("testMemberPhone1");
        member.setMemberAddress("testMemberAddress1");
        member.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member);

        Item item = new Item();
        item.setName("testItemId");
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId(member.getMemberId());
        itemService.saveItem(item);

        Item updateItem = new Item();
        updateItem.setItemId(item.getItemId());
        updateItem.setName("updatedItemId");
        updateItem.setStock(0);
        updateItem.setPrice(2);
        updateItem.setIsSoldout(true);
        updateItem.setSellerId(member.getMemberId());

        //When
        int result = itemService.updateItem(updateItem);

        //Then
        Item savedItem = itemService.findOne(item.getItemId());
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("updatedItemId");
        assertThat(savedItem.getStock()).isZero();
        assertThat(savedItem.getPrice()).isEqualTo(2);
        assertThat(savedItem.getIsSoldout()).isTrue();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void 상품_품절여부_업데이트() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId1");
        member.setMemberName("testMemberName1");
        member.setMemberPassword("testMemberPassword1");
        member.setMemberPhone("testMemberPhone1");
        member.setMemberAddress("testMemberAddress1");
        member.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member);

        Item item = new Item();
        item.setName("testItemId");
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId(member.getMemberId());
        itemService.saveItem(item);

        //When
        int result = itemService.updateIsSoldout(item.getItemId());

        //Then
        Item savedItem = itemService.findOne(item.getItemId());
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getName()).isEqualTo(item.getName());
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void 상품_재고량과_판매량_업데이트() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId1");
        member.setMemberName("testMemberName1");
        member.setMemberPassword("testMemberPassword1");
        member.setMemberPhone("testMemberPhone1");
        member.setMemberAddress("testMemberAddress1");
        member.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member);

        Item item = new Item();
        item.setName("testItemId");
        item.setStock(3);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId(member.getMemberId());
        itemService.saveItem(item);

        //When
        int result1 = itemService.updateStock(item.getItemId(), 2);
        int result2 = itemService.updateCount(item.getItemId(), 1);

        //Then
        Item savedItem = itemService.findOne(item.getItemId());
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getStock()).isEqualTo(1);
        assertThat(savedItem.getCount()).isEqualTo(1);
        assertThat(result1).isGreaterThan(0);
        assertThat(result2).isGreaterThan(0);
    }

    @Test
    public void 상품_삭제() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId1");
        member.setMemberName("testMemberName1");
        member.setMemberPassword("testMemberPassword1");
        member.setMemberPhone("testMemberPhone1");
        member.setMemberAddress("testMemberAddress1");
        member.setMemberEmail("testMemberEmail1");
        memberDAO.insertMember(member);

        Item item = new Item();
        item.setName("testItemId");
        item.setStock(3);
        item.setPrice(1);
        item.setIsSoldout(false);
        item.setSellerId(member.getMemberId());
        itemService.saveItem(item);

        //When
        int result = itemService.deleteItem(item.getItemId());

        //Then
        assertThat(result).isGreaterThan(0);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->itemService.findOne(item.getItemId()));
        assertThat(e.getMessage()).isEqualTo("해당 itemId를 가진 상품이 존재하지 않습니다.");
    }
}
