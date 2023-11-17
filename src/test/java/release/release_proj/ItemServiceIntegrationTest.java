package release.release_proj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Item;
import release.release_proj.repository.ItemRepository;
import release.release_proj.service.ItemService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        item.setPrice(1);
        item.setIsSoldout(true);

        //When
        int result = itemService.saveItem(item);

        //Then
        Optional<Item> savedItem = itemService.findOne(item.getItemId());
        assertThat(savedItem).isPresent();
        assertEquals(item.getName(), savedItem.get().getName());
    }

    @Test
    public void 상품등록중복예외처리() throws Exception {
        //Given
        Item item1 = new Item();
        item1.setName("testItemId");
        item1.setPrice(1);
        item1.setIsSoldout(true);

        Item item2 = new Item();
        item2.setName("testItemId");
        item2.setPrice(1);
        item2.setIsSoldout(true);

        //When
        int result = itemService.saveItem(item1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()->itemService.saveItem(item2)); //예외가 발생해야 함

        //Then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
    }

}
