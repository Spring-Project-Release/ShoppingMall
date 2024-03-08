package release.release_proj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.MemberVO;
import release.release_proj.dto.ItemRequestDTO;
import release.release_proj.dto.ItemResponseDTO;
import release.release_proj.repository.MemberDAO;
import release.release_proj.service.ItemService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ItemServiceIntegrationTest {

    @PersistenceContext private EntityManager em;

    @Autowired ItemService itemService;
    @Autowired MemberDAO memberDAO;

    @Test
    public void 상품등록() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        ItemRequestDTO itemDTO = ItemRequestDTO.builder()
                .name("testItemName")
                .stock(1)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        //When
        itemService.saveItem(itemDTO);
        
        //Then
        ItemResponseDTO savedItemDTO = itemService.findByItemName(itemDTO.getName());
        assertThat(savedItemDTO).isNotNull();
        assertThat(itemDTO.getName()).isEqualTo(savedItemDTO.getName());
    }

    @Test
    public void 상품등록_외래키_예외_판매자_없음() throws Exception {
        //Given
        ItemRequestDTO itemDTO = ItemRequestDTO.builder()
                .name("testItemName")
                .stock(1)
                .price(1)
                .isSoldout(false)
                .sellerId("testSellerId")
                .build();

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> itemService.saveItem(itemDTO)); //예외가 발생해야 함
        assertThat(e.getMessage()).isEqualTo("해당하는 상품의 sellerId가 유저 DB에 존재하지 않습니다.");
    }

    @Test
    public void 상품등록_예외_상품이름_중복() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        ItemRequestDTO itemDTO1 = ItemRequestDTO.builder()
                .name("testItemName")
                .stock(1)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        ItemRequestDTO itemDTO2 = ItemRequestDTO.builder()
                .name("testItemName")
                .stock(1)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        //When
        itemService.saveItem(itemDTO1);

        //Then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> itemService.saveItem(itemDTO2)); //예외가 발생해야 함
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
    }

    @Test
    public void 상품_최근생성순_정렬() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        ItemRequestDTO itemDTO1 = ItemRequestDTO.builder()
                .name("testItemName1")
                .stock(1)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        ItemRequestDTO itemDTO2 = ItemRequestDTO.builder()
                .name("testItemName2")
                .stock(1)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        itemService.saveItem(itemDTO1);
        itemService.saveItem(itemDTO2);
        ItemResponseDTO savedItemDTO1 = itemService.findByItemName(itemDTO1.getName());
        ItemResponseDTO savedItemDTO2 = itemService.findByItemName(itemDTO2.getName());

        List<ItemResponseDTO> itemList = itemService.readOrderByCreatedAtDesc(1, 20);
        assertThat(itemList.get(0).getItemId()).isEqualTo(savedItemDTO1.getItemId());
        assertThat(itemList.get(1).getItemId()).isEqualTo(savedItemDTO2.getItemId());
    }

    @Test
    public void 상품_판매량순_정렬() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        ItemRequestDTO itemDTO1 = ItemRequestDTO.builder()
                .name("testItemName1")
                .stock(1)
                .count(2)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        ItemRequestDTO itemDTO2 = ItemRequestDTO.builder()
                .name("testItemName2")
                .stock(1)
                .count(3)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        itemService.saveItem(itemDTO1);
        itemService.saveItem(itemDTO2);
        ItemResponseDTO savedItemDTO1 = itemService.findByItemName(itemDTO1.getName());
        ItemResponseDTO savedItemDTO2 = itemService.findByItemName(itemDTO2.getName());

        List<ItemResponseDTO> itemList = itemService.readOrderByCountDesc(1, 20);
        List<ItemResponseDTO> filteredItemList = itemList.stream() //test에서 저장된 item들만 filetering
                .filter(item -> item.getItemId() == savedItemDTO1.getItemId() || item.getItemId() == savedItemDTO2.getItemId())
                .collect(Collectors.toList());

        for (int i = 1; i < itemList.size(); i++) {
            assertThat(itemList.get(i - 1).getCount()).isGreaterThanOrEqualTo(itemList.get(i).getCount());
        }

        assertThat(filteredItemList.get(0).getItemId()).isEqualTo(savedItemDTO2.getItemId());
        assertThat(filteredItemList.get(1).getItemId()).isEqualTo(savedItemDTO1.getItemId());
    }

    @Test
    public void 상품_업데이트() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        ItemRequestDTO itemDTO = ItemRequestDTO.builder()
                .name("testItemName")
                .stock(1)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        itemService.saveItem(itemDTO);
        ItemResponseDTO savedItemDTO = itemService.findByItemName(itemDTO.getName());

        ItemRequestDTO updateItemDTO = ItemRequestDTO.builder()
                .itemId(savedItemDTO.getItemId())
                .name("updatedItemName")
                .stock(0)
                .price(2)
                .isSoldout(true)
                .sellerId(member.getMemberId())
                .build();

        //When
        itemService.updateItem(updateItemDTO);

        //Then
        ItemResponseDTO savedItemDTO2 = itemService.findOne(savedItemDTO.getItemId());
        assertThat(savedItemDTO2).isNotNull();
        assertThat(savedItemDTO2.getName()).isEqualTo("updatedItemName");
        assertThat(savedItemDTO2.getStock()).isZero();
        assertThat(savedItemDTO2.getPrice()).isEqualTo(2);
        assertThat(savedItemDTO2.getIsSoldout()).isTrue();
    }

    @Test
    public void 상품_품절여부_업데이트() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        ItemRequestDTO itemDTO = ItemRequestDTO.builder()
                .name("testItemName")
                .stock(1)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        itemService.saveItem(itemDTO);
        ItemResponseDTO savedItemDTO = itemService.findByItemName(itemDTO.getName());

        //When
        int result = itemService.updateIsSoldout(savedItemDTO.getItemId());

        //Then
        ItemResponseDTO savedItemDTO2 = itemService.findOne(savedItemDTO.getItemId());
        assertThat(savedItemDTO2).isNotNull();
        assertThat(savedItemDTO2.getName()).isEqualTo(itemDTO.getName());
        assertThat(result).isGreaterThan(0);
    }

    /*@Test
    public void 상품_재고량과_판매량_업데이트() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        ItemRequestDTO itemDTO = ItemRequestDTO.builder()
                .name("testItemName")
                .stock(3)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        itemService.saveItem(itemDTO);
        ItemResponseDTO savedItemDTO = itemService.findByItemName(itemDTO.getName());

        //When
        int result1 = itemService.updateStock(savedItemDTO.getItemId(), 2);
        em.flush();
        em.clear();
        int result2 = itemService.updateCount(savedItemDTO.getItemId(), 1);

        //Then
        ItemResponseDTO savedItemDTO2 = itemService.findOne(savedItemDTO.getItemId());
        assertThat(savedItemDTO2).isNotNull();
        assertThat(savedItemDTO2.getStock()).isEqualTo(1);
        assertThat(savedItemDTO2.getCount()).isEqualTo(1);
        assertThat(result1).isGreaterThan(0);
        assertThat(result2).isGreaterThan(0);
    }*/

    @Test
    public void 상품_삭제() throws Exception {
        //Given
        MemberVO member = new MemberVO();
        member.setMemberId("testMemberId");
        member.setMemberName("testMemberName");
        member.setMemberPassword("testMemberPassword");
        member.setMemberPhone("testMemberPhone");
        member.setMemberAddress("testMemberAddress");
        member.setMemberEmail("testMemberEmail");
        memberDAO.insertMember(member);

        ItemRequestDTO itemDTO = ItemRequestDTO.builder()
                .name("testItemName")
                .stock(3)
                .price(1)
                .isSoldout(false)
                .sellerId(member.getMemberId())
                .build();

        itemService.saveItem(itemDTO);

        //When
        ItemResponseDTO savedItemDTO = itemService.findByItemName(itemDTO.getName());
        Long savedItemId = savedItemDTO.getItemId();
        itemService.deleteItem(savedItemDTO.getItemId());

        //Then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()-> itemService.findOne(savedItemId));
        assertThat(e.getMessage()).isEqualTo("해당 itemId를 가진 상품이 존재하지 않습니다.");
    }

}
