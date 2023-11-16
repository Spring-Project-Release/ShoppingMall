/*package release.release_proj.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Item;
import release.release_proj.repository.ItemRepository;
import release.release_proj.service.ItemService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ItemTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품등록() throws Exception {
        //Given
        Item item = new Item();
        item.setName("item1");
        //When
        Long saveId = itemService.saveItem(item);
        //Then
        Item findItem = itemRepository.findByItemId(saveId).get();
        assertEquals(item.getName(), findItem.getName());
    }

    @Test
    public void 상품중복예외처리() throws Exception {
        //Given
        Item item1 = new Item();
        item1.setName("item");

        Item item2 = new Item();
        item2.setName("item");
        //When
        itemService.saveItem(item1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()->itemService.saveItem(item2)); //예외가 발생해야 함

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
    }

    @Test
    public void 상품update() throws Exception {
        //Given
        Item item = new Item();
        item.setName("item1");
        Long saveId = itemService.saveItem(item);
        //When
        item.setName("item2");
        itemService.updateItem(item);
        //Then
        Item findItem = itemRepository.findByItemId(saveId).get();
        assertEquals(item.getName(), findItem.getName()); //item2로 이름이 일치하는지 확인
    }
}
*/