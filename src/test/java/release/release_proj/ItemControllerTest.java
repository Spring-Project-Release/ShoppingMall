package release.release_proj;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import release.release_proj.controller.ItemController;
import release.release_proj.domain.MemberVO;
import release.release_proj.dto.ItemRequestDTO;
import release.release_proj.dto.ItemResponseDTO;
import release.release_proj.service.MemberService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ItemControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired MemberService memberService;
    @Autowired private ItemController itemController;

    /*@BeforeEach
    //public void setUp(WebApplicationContext webApplicationContext) {
    //    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }*/

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @BeforeEach
    public void userSetUp() {
        // 사용자 등록
        MemberVO memberVO = new MemberVO();
        memberVO.setMemberId("testMemberId");
        memberVO.setMemberName("testMember");
        memberVO.setMemberPassword("testMemberPassword");
        memberVO.setMemberPhone("testMemberPhone");
        memberVO.setMemberEmail("testMember@test");
        memberVO.setMemberAddress("testMemberAddress");
        memberService.insertMember(memberVO);
    }

    @Test
    @DisplayName("상품 등록 테스트")
    public void testNewItem() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
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
    @DisplayName("상품 등록 판매자 존재하지 않음 예외 테스트")
    public void testNewItemWithNonExistingSeller() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .category("Test Category")
                .price(1000)
                .isSoldout(true)
                .sellerId("testMemberId2")
                .build();

        mockMvc.perform(
                        post("/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newItem))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("해당하는 상품의 sellerId가 유저 DB에 존재하지 않습니다."));
    }

    @Test
    @DisplayName("상품 등록 이름 중복 예외 테스트")
    public void testNewItemWithDuplicateName() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andExpect(status().isOk());

        mockMvc.perform(
                        post("/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newItem))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오."));
    }

    @Test
    @DisplayName("상품 목록 조회 테스트")
    public void testItemList() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()); // JSON Array로 응답되는지 확인
    }

    /*@Test
    @DisplayName("등록일자 기준 상품 목록 조회 테스트")
    public void testItemListByCreatedAt() throws Exception {
        // 상품 등록
        ItemRequestDTO newItem1 = ItemRequestDTO.builder()
                .name("Item1")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(false)
                .sellerId("testMemberId")
                .build();

        ItemRequestDTO newItem2 = ItemRequestDTO.builder()
                .name("Item2")
                .price(2000)
                .count(0)
                .stock(10)
                .isSoldout(false)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem1))
        ).andExpect(status().isOk());

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem2))
        ).andExpect(status().isOk());

        // 상품 목록 조회 API 호출
        MvcResult mvcResult = mockMvc.perform(get("/items/createdAt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        //ItemResponseDTO itemResponseDTO = objectMapper.readValue(content, ItemResponseDTO.class);
        //Long itemId = itemResponseDTO.getItemId();

        // 상품 목록에서 등록일자 확인

        //LocalDateTime createdAt1 = itemList.get(0).getCreatedAt();
        //LocalDateTime createdAt2 = itemList.get(1).getCreatedAt();

        // 등록일자가 실제로 등록된 순서와 일치하는지 확인
        //assertThat(createdAt1).isAfterOrEqualTo(createdAt2);
    }*/

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
        // 상품 등록
        ItemRequestDTO newItem1 = ItemRequestDTO.builder()
                .name("Item1")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(false)
                .category("testCategory1")
                .sellerId("testMemberId")
                .build();

        ItemRequestDTO newItem2 = ItemRequestDTO.builder()
                .name("Item2")
                .price(2000)
                .count(0)
                .stock(10)
                .category("testCategory2")
                .isSoldout(false)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem1))
        ).andExpect(status().isOk());

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem2))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/items/category/{category}", "testCategory1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("카테고리별 상품 목록 조회 해당 카테고리를 가지는 상품이 존재하지 않는 예외 테스트")
    public void testGetItemByCategoryException() throws Exception {
        mockMvc.perform(get("/items/category/{category}", "testCategory0"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("품절 여부에 따른 상품 목록 조회 테스트 - true인 경우가 있는 경우")
    public void testGetItemByIsSoldout() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                        post("/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newItem))
                ).andExpect(status().isOk());

        mockMvc.perform(get("/items/isSoldout/{isSoldout}", true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("상품 ID로 상품 조회 테스트")
    public void testGetItemById() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(
                get("/items/name/{name}",newItem.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ItemResponseDTO itemResponseDTO = objectMapper.readValue(content, ItemResponseDTO.class);
        Long itemId = itemResponseDTO.getItemId();

        mockMvc.perform(get("/items/id/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.itemId").value(1));
    }

    @Test
    @DisplayName("상품 이름으로 상품 조회 테스트")
    public void testGetItemByName() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/items/name/{name}", "TestItem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("판매자 ID로 상품 목록 조회 테스트")
    public void testGetItemBySellerId() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/items/sellerId/{sellerId}", "testMemberId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("상품 업데이트 테스트")
    public void testUpdateItem() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(
                get("/items/name/{name}",newItem.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ItemResponseDTO itemResponseDTO = objectMapper.readValue(content, ItemResponseDTO.class);
        Long itemId = itemResponseDTO.getItemId();

        ItemRequestDTO updatedItemDTO = ItemRequestDTO.builder()
                .name("TestItemUpdated")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
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
        ItemRequestDTO updatedItemDTO = ItemRequestDTO.builder()
                .name("TestItemUpdated")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(put("/items/{itemId}", 9999999999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItemDTO))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("존재하지 않는 itemId입니다."));
    }

    @Test
    @DisplayName("판매 품절 여부 업데이트 테스트")
    public void testUpdateIsSoldout() throws Exception {
        ItemRequestDTO newItem = ItemRequestDTO.builder()
                .name("TestItem")
                .price(1000)
                .count(0)
                .stock(10)
                .isSoldout(true)
                .sellerId("testMemberId")
                .build();

        mockMvc.perform(
                post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(
                get("/items/name/{name}",newItem.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem))
        ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ItemResponseDTO itemResponseDTO = objectMapper.readValue(content, ItemResponseDTO.class);
        Long itemId = itemResponseDTO.getItemId();

        mockMvc.perform(put("/items/{itemId}/isSoldout", itemId))
                .andExpect(status().isOk())
                .andExpect(content().string("Item isSoldout updated successfully."));
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void testDeleteItem() throws Exception {
        Long itemId = 1L; // 삭제할 아이템의 ID

        mockMvc.perform(delete("/items/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(content().string("Item deleted successfully."));
    }

    @Test
    @DisplayName("상품 삭제 테스트 - 이미 삭제된 상품이거나 상품 ID가 존재하지 않는 경우")
    public void testDeleteItemException() throws Exception {
        Long itemId = 99999999L; // 삭제할 아이템의 ID

        mockMvc.perform(delete("/items/{itemId}", itemId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("존재하지 않는 itemId입니다."));
    }
}
