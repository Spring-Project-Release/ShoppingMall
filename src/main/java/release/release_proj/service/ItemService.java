package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Item;
import release.release_proj.dto.ItemRequestDTO;
import release.release_proj.dto.ItemResponseDTO;
import release.release_proj.repository.ItemRepository;
import release.release_proj.repository.MemberDAO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberDAO memberDAO;

    public void saveItem(ItemRequestDTO itemDTO){
        validateSellerExistence(itemDTO.getSellerId()); //해당 seller가 실제로 존재하는 유저인지 확인
        isItemNameDuplicate(itemDTO);
        itemRepository.save(itemDTO.toEntity());
    }

    public void isItemNameDuplicate(ItemRequestDTO itemDTO){
        itemRepository.findByItemName(itemDTO.getName())
                .ifPresent(i -> {
                    throw new IllegalStateException("이미 존재하는 상품 이름입니다. 이름을 변경해 주십시오.");
                });
    }

    private void validateSellerExistence(String sellerId) {
        if (memberDAO.isExistMemberId(sellerId)==0) {
            throw new IllegalArgumentException("해당하는 상품의 sellerId가 유저 DB에 존재하지 않습니다.");
        }
    }

    public void updateItem(ItemRequestDTO itemDTO){
        itemRepository.updateItem(itemDTO.toEntity());
    }

    public void deleteItem(Long itemId){
        itemRepository.deleteByItemId(itemId);
    }

    public ItemResponseDTO findOne(Long itemId) {
        Item item = itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 itemId를 가진 상품이 존재하지 않습니다."));

        return new ItemResponseDTO(item);
    }

    public ItemResponseDTO findByItemName(String name) {
        Item item = itemRepository.findByItemName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 name을 가진 상품이 존재하지 않습니다."));

        return new ItemResponseDTO(item);
    }

    public List<ItemResponseDTO> readItems(int page, int size) {
        List<Item> items =  itemRepository.findAll((page-1)*size, size);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        return items.stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

    public List<ItemResponseDTO> readOrderByCreatedAtDesc(int page, int size) {
        List<Item> items =  itemRepository.findAllOrderByCreatedAtDesc((page-1)*size, size);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        return items.stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

    public List<ItemResponseDTO> readOrderByCountDesc(int page, int size) {
        List<Item> items =  itemRepository.findAllOrderByCountDesc((page-1)*size, size);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        return items.stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

    public List<ItemResponseDTO> findItemsBySellerId(String sellerId, int page, int size) {
        List<Item> items = itemRepository.findBySellerId(sellerId, (page-1)*size, size);

        if (items.isEmpty()) {
            throw new IllegalArgumentException("해당 sellerId를 가진 상품이 존재하지 않습니다.");
        }

        return items.stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

    public List<ItemResponseDTO> findByIsSoldout(Boolean isSoldout, int page, int size) {
        List<Item> items = itemRepository.findByIsSoldout(isSoldout, (page-1)*size, size);

        if (items.isEmpty()) {
            throw new IllegalArgumentException("해당 품절여부 조건을 만족하는 상품이 존재하지 않습니다.");
        }

        return items.stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

    public List<ItemResponseDTO> findByCategory(String category, int page, int size) {
        List<Item> items = itemRepository.findByCategory(category, (page-1)*size, size);

        if (items.isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리를 가진 상품이 존재하지 않습니다.");
        }

        return items.stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

    public int updateIsSoldout(Long itemId) { //나중에 itemName을 인자로 사용할수도?
        return itemRepository.updateIsSoldout(itemId);
    }

    public int updateStock(Long itemId, int decreasingStock) {
        return itemRepository.updateStock(itemId, decreasingStock);
    }

    public int getStock(Long itemId) {
        return itemRepository.getStock(itemId);
    }

    public int getPrice(Long itemId) {
        return itemRepository.getPrice(itemId);
    }

    public int updateCount(Long itemId, int increasingCount){
        return itemRepository.updateCount(itemId, increasingCount);
    }

    public int isItemIdExist(Long itemId) {
        return itemRepository.isItemIdExist(itemId);
    }
}
