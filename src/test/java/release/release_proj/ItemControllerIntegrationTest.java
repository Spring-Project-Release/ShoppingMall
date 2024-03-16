package release.release_proj;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import release.release_proj.domain.MemberVO;
import release.release_proj.dto.ItemRequestDTO;
import release.release_proj.service.ItemService;
import release.release_proj.service.MemberService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private ItemService itemService;
    @MockBean private MemberService memberService;

    @BeforeAll
    public static void setUp(@Autowired ItemService itemService, @Autowired MemberService memberService) {
        MemberVO memberVO = new MemberVO();
        memberVO.setMemberId("Test Member0");
        memberVO.setMemberEmail("test0@example.com");

        memberService.insertMember(memberVO);

        // 기본 상품 추가
        ItemRequestDTO newItem1 = ItemRequestDTO.builder()
                .itemId(1L)
                .name("TestItem0")
                .category("Test Category0")
                .isSoldout(true)
                .price(1000)
                .sellerId("Test Member0")
                .build();

        ItemRequestDTO newItem2 = ItemRequestDTO.builder()
                .itemId(2L)
                .name("TestItem00")
                .category("Test Category00")
                .isSoldout(true)
                .price(1000)
                .sellerId("Test Member0")
                .build();

        itemService.saveItem(newItem1);
        itemService.saveItem(newItem2);
    }

    /*@Test
    @DisplayName("상품 등록 테스트")
    public void testNewItem() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("Test Item")
                .category("Test Category")
                .isSoldout(true)
                .price(1000)
                .sellerId("Test Member0")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Item created successfully."));
    }

    @Test
    @DisplayName("상품 등록 중복 예외 테스트")
    public void testNewItemWithDuplicateName() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("Test Item")
                .category("Test Category")
                .isSoldout(true)
                .price(1000)
                .sellerId("Test Member0")
                .build();

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newItem)));

        // Try to create item with the same name
        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오."));
    }

    @Test
    @DisplayName("상품 등록 판매자 존재하지 않음 예외 테스트")
    public void testNewItemWithNonExistingSeller() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("Test Item")
                .category("Test Category")
                .price(1000)
                .isSoldout(true)
                .sellerId("Test Member0")
                .build();

        mockMvc.perform(
                        post("/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newItem))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("해당하는 상품의 sellerId가 유저 DB에 존재하지 않습니다."));
    }*/

    @Test
    @DisplayName("상품 목록 조회 테스트")
    public void testItemList() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()); // JSON Array로 응답되는지 확인
    }

    @Test
    @DisplayName("등록일자 기준 상품 목록 조회 테스트")
    public void testItemListByCreatedAt() throws Exception {
        mockMvc.perform(get("/items/createdAt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("판매량 기준 상품 목록 조회 테스트")
    public void testItemListByCount() throws Exception {
        mockMvc.perform(get("/items/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("카테고리별 상품 목록 조회 테스트")
    public void testGetItemByCategory() throws Exception {
        mockMvc.perform(get("/items/category/{category}", "testCategory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /*@Test
    @DisplayName("카테고리별 상품 목록 조회 해당 카테고리를 가지는 상품이 존재하지 않는 예외 테스트")
    public void testGetItemByCategoryException() throws Exception {
        mockMvc.perform(get("/items/category/{category}", "testCategory0"))
                .andExpect(status().isNotFound());
    }*/

    @Test
    @DisplayName("품절 여부에 따른 상품 목록 조회 테스트 - true인 경우가 있는 경우")
    public void testGetItemByIsSoldout() throws Exception {
        mockMvc.perform(get("/items/isSoldout/{isSoldout}", true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /*@Test
    @DisplayName("품절 여부에 따른 상품 목록 조회 테스트 - false인 경우가 없는 경우")
    public void testGetItemByIsSoldoutException() throws Exception {
        mockMvc.perform(get("/items/isSoldout/{isSoldout}", false))
                .andExpect(status().isNotFound());
    }*/

    /*@Test
    @DisplayName("상품 ID로 상품 조회 테스트")
    public void testGetItemById() throws Exception {
        mockMvc.perform(get("/items/id/{itemId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.itemId").value(1)); // 예시로 itemId가 1인 상품 조회 확인
    }

    @Test
    @DisplayName("상품 이름으로 상품 조회 테스트")
    public void testGetItemByName() throws Exception {
        mockMvc.perform(get("/items/name/{name}", "TestItem0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("TestItem0")); // 예시로 이름이 "Test Item0"인 상품 조회 확인
    }

    @Test
    @DisplayName("판매자 ID로 상품 목록 조회 테스트")
    public void testGetItemBySellerId() throws Exception {
        mockMvc.perform(get("/items/sellerId/{sellerId}", "TestMember0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(20));
    }*/

    /*@Test
    @DisplayName("상품 업데이트 테스트")
    public void testUpdateItem() throws Exception {
        Long itemId = 1L; // 업데이트할 아이템의 ID
        ItemRequestDTO updatedItemDTO = ItemRequestDTO.builder()
                .name("Updated Test Item")
                .category("Updated Test Category")
                .price(1500)
                .build();

        mockMvc.perform(put("/items/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItemDTO))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Item updated successfully."));
    }

    @Test
    @DisplayName("상품 ID로 상품 업데이트 테스트 - 해당 상품 ID가 존재하지 않는 경우")
    public void testGetItemByIdException() throws Exception {
        mockMvc.perform(put("/items/id/{itemId}", 1L))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("존재하지 않는 itemId입니다."));
    }

    @Test
    @DisplayName("판매 여부 업데이트 테스트")
    public void testUpdateIsSoldout() throws Exception {
        Long itemId = 1L; // 판매 여부를 업데이트할 아이템의 ID

        mockMvc.perform(put("/items/{itemId}/isSoldout", itemId))
                .andExpect(status().isOk())
                .andExpect(content().string("Item isSoldout updated successfully."));
    }*/

    @Test
    @DisplayName("상품 삭제 테스트")
    public void testDeleteItem() throws Exception {
        Long itemId = 1L; // 삭제할 아이템의 ID

        mockMvc.perform(delete("/items/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(content().string("Item deleted successfully."));
    }

    /*@Test
    @DisplayName("상품 삭제 테스트 - 이미 삭제된 상품이거나 상품 ID가 존재하지 않는 경우")
    public void testDeleteItemException() throws Exception {
        Long itemId = 4L; // 삭제할 아이템의 ID

        mockMvc.perform(delete("/items/{itemId}", itemId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("존재하지 않는 itemId입니다."));
    }*/
}
