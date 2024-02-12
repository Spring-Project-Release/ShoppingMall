package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Cart;
import release.release_proj.dto.CartRequestDTO;
import release.release_proj.dto.CartResponseDTO;
import release.release_proj.dto.ItemResponseDTO;
import release.release_proj.dto.OrderRequestDTO;
import release.release_proj.repository.CartRepository;
import release.release_proj.repository.MemberDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //생성자 주입을 대신 해줌
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberDAO memberDAO;

    public List<CartResponseDTO> readMemberCarts(String memberId, int page, int size) {
        List<Cart> cartList = cartRepository.findByMemberId(memberId, (page-1)*size, size);
        if (cartList.isEmpty()) {
            throw new IllegalArgumentException("해당 memberId를 가지는 장바구니가 존재하지 않습니다.");
        }

        return cartList.stream().map(CartResponseDTO::new).collect(Collectors.toList());
    }

    public CartResponseDTO readMemberCartItems(String memberId, Long itemId) {
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 memberId와 itemId를 가지는 장바구니가 존재하지 않습니다."));

        return new CartResponseDTO(cart);
    }

    public void addCartItem(CartRequestDTO cartDTO) {
        try {
            ItemResponseDTO itemDTO = itemService.findOne(cartDTO.getItemId());
            if (cartDTO.getMemberId().equals(itemDTO.getSellerId())) {
                throw new IllegalArgumentException("본인이 판매중인 상품을 장바구니에 담을 수 없습니다.");
            }

            Optional<Cart> isCartExist = cartRepository.findByMemberIdAndItemId(cartDTO.getMemberId(), cartDTO.getItemId());
            if (isCartExist.isPresent()){
                Cart currentCart = isCartExist.get();
                cartRepository.updateCartAmount(currentCart.getCartId(), cartDTO.getAmount());
            }
            else {
                cartRepository.save(cartDTO.toEntity());
            }
        } catch (DataIntegrityViolationException e) {
            // 외래 키 제약 조건 위배로 인한 예외 처리
            throw new IllegalArgumentException("Failed to create cart. 해당하는 itemId나 memberId가 존재하지 않습니다", e);
        }
    }

    public int deleteCartItem(String memberId, Long itemId) {
        Optional<Cart> isCartItemExist = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (isCartItemExist.isPresent()) {
            return cartRepository.deleteByMemberIdAndItemId(memberId, itemId);
        } else {
            return 0;
        }
    }

    public int deleteCart(String memberId, int page, int size) {
        List<Cart> memberCarts = cartRepository.findByMemberId(memberId, (page-1)*size, size);

        if (!memberCarts.isEmpty()) {
            return cartRepository.deleteByMemberId(memberId);
        } else {
            return 0;
        }
    }

    public int decreaseCartItem(Long cartId) {
        int amount = -1;
        int result = cartRepository.updateCartAmount(cartId, amount);
        cartRepository.deleteCartIfAmountIsZero(cartId); //이게 제대로 작동하지 않았을 때의 예외처리를 해주어야 할까?

        return result;
    }

    public int increaseCartItem(Long cartId, int amount) { //증가의 경우 상품페이지에서 바로 담을 수 있으므로 한번에 여러개의 상품이 증가할 수 있음

        return cartRepository.updateCartAmount(cartId, amount);
    }

    public void payAllCart(String memberId, int page, int size, String memo) {
        if (memberDAO.isExistMemberId(memberId) == 0) { //memberId 자체가 member db에 존재하지 않는 경우
            throw new IllegalArgumentException("Invalid memberId: " + memberId);
        }

        List<Cart> carts = cartRepository.findByMemberId(memberId, (page-1)*size, size);
        // 장바구니가 비어있는지 확인
        if (carts.isEmpty()) { //해당 memberId 자체는 존재하지만 cart DB에 존재하지 않는 경우
            throw new IllegalArgumentException("해당하는 memberId: " + memberId + "를 갖는 cart가 존재하지 않습니다.");
        } else {
            for (Cart cart : carts) {
                //order 기록 저장- 구매내역으로 무언가를 할 경우(user의 등급을 매길 경우 user에 paymentPrice 항목도 추가를 해야하나?)
                ItemResponseDTO itemDTO = itemService.findOne(cart.getItemId());

                OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                        .buyerId(cart.getMemberId())
                        .itemId(cart.getItemId())
                        .sellerId(itemDTO.getSellerId())
                        .count(cart.getAmount())
                        .price(cart.getAmount() * itemService.getPrice(cart.getItemId()))
                        .memo(memo != null ? memo : "") //controller에서 memo를 받아와서 order에 set해줌
                        .build();

                orderService.save(orderDTO);
                cartRepository.deleteByMemberIdAndItemId(memberId, cart.getItemId());
            }
        }
    }

    public void paySomeCart(String memberId, List<Long> itemsId, String memo) {
        if (memberDAO.isExistMemberId(memberId) == 0) { //memberId 자체가 member db에 존재하지 않는 경우
            throw new IllegalArgumentException("Invalid memberId: " + memberId);
        }

        for (Long itemId : itemsId) {
            if (itemService.isItemIdExist(itemId)==0){ //itemId 자체가 item db에 존재하지 않는 경우
                throw new IllegalArgumentException("Invalid itemId: " + itemId);
            }

            Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId).orElseThrow(() ->
                    new IllegalArgumentException("해당하는 itemId: " + itemId + "와 memberId: " + memberId + "를 갖는 cart가 존재하지 않습니다.")
            );  //해당 itemId와 memberId 자체는 존재하지만 cart DB에 존재하지 않는 경우

            ItemResponseDTO itemDTO = itemService.findOne(cart.getItemId());
            // 주문 생성
            OrderRequestDTO orderDTO = OrderRequestDTO.builder()
                    .buyerId(cart.getMemberId())
                    .itemId(cart.getItemId())
                    .sellerId(itemDTO.getSellerId())
                    .count(cart.getAmount())
                    .price(cart.getAmount() * itemService.getPrice(cart.getItemId()))
                    .memo(memo != null ? memo : "")
                    .build();

            orderService.save(orderDTO);
            cartRepository.deleteByMemberIdAndItemId(memberId, itemId);
        }
    }
}