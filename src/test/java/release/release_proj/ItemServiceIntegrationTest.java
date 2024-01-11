package release.release_proj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Item;
import release.release_proj.repository.ItemRepository;
import release.release_proj.service.ItemService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemServiceIntegrationTest {

    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;

    @Test
    public void 상품등록() throws Exception {
        //Given
        Item item = new Item();
        item.setName("testItemId");
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);

        //When
        int result = itemService.saveItem(item);

        //Then
        Item savedItem = itemService.findOne(item.getItemId());
        assertThat(savedItem).isNotNull();
        assertThat(item.getName()).isEqualTo(savedItem.getName());
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void 상품등록_예외_중복() throws Exception {
        //Given
        Item item1 = new Item();
        item1.setName("testItemId");
        item1.setStock(1);
        item1.setPrice(1);
        item1.setIsSoldout(false);

        Item item2 = new Item();
        item2.setName("testItemId");
        item2.setStock(1);
        item2.setPrice(1);
        item2.setIsSoldout(false);

        //When
        int result = itemService.saveItem(item1);

        //Then
        IllegalStateException e = assertThrows(IllegalStateException.class, ()->itemService.saveItem(item2)); //예외가 발생해야 함
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
    }

    @Test
    public void 상품_업데이트() throws Exception {
        //Given
        Item item = new Item();
        item.setName("testItemId");
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
        itemService.saveItem(item);

        Item updateItem = new Item();
        updateItem.setItemId(item.getItemId());
        updateItem.setName("updatedItemId");
        updateItem.setStock(0);
        updateItem.setPrice(2);
        updateItem.setIsSoldout(true);

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
        Item item = new Item();
        item.setName("testItemId");
        item.setStock(1);
        item.setPrice(1);
        item.setIsSoldout(false);
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
        Item item = new Item();
        item.setName("testItemId");
        item.setStock(3);
        item.setPrice(1);
        item.setIsSoldout(false);
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
        Item item = new Item();
        item.setName("testItemId");
        item.setStock(3);
        item.setPrice(1);
        item.setIsSoldout(false);
        itemService.saveItem(item);

        //When
        int result = itemService.deleteItem(item.getItemId());

        //Then
        assertThat(result).isGreaterThan(0);
        IllegalStateException e = assertThrows(IllegalStateException.class, ()->itemService.findOne(item.getItemId()));
        assertThat(e.getMessage()).isEqualTo("해당 itemId를 가진 상품이 존재하지 않습니다.");
    }
}
