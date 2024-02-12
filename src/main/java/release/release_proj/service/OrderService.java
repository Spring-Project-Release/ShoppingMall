package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Order;
import release.release_proj.dto.OrderRequestDTO;
import release.release_proj.dto.OrderResponseDTO;
import release.release_proj.repository.MemberDAO;
import release.release_proj.repository.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final MemberDAO memberDAO;

    public void save(OrderRequestDTO orderDTO) {
        validateMemberAndItemExistence(orderDTO.getBuyerId(), orderDTO.getItemId(), orderDTO.getSellerId());
        int currentStock = itemService.getStock(orderDTO.getItemId());

        if (orderDTO.getBuyerId().equals(orderDTO.getSellerId())) {
            throw new IllegalArgumentException("본인이 판매중인 상품을 구매할 수 없습니다.");
        }
        if (currentStock < orderDTO.getCount()) {
            throw new IllegalStateException(orderDTO.getItemId()+" 상품의 재고가 부족합니다.");
        }

        orderRepository.save(orderDTO.toEntity());
        itemService.updateStock(orderDTO.getItemId(), orderDTO.getCount()); //item 재고 감소
        itemService.updateCount(orderDTO.getItemId(), orderDTO.getCount()); //item 판매개수 상승

        if (currentStock - orderDTO.getCount() == 0) { //재고가 0인 경우
            itemService.updateIsSoldout(orderDTO.getItemId());
        }
    };

    private void validateMemberAndItemExistence(String buyerId, Long itemId, String sellerId) {
        // buyerId와 itemId가 존재하는지 여부 확인(외래 키 제약 조건 위배 여부 확인)
        if (memberDAO.isExistMemberId(buyerId)==0) {
            throw new IllegalArgumentException("결제 처리에 실패했습니다. 해당하는 buyerId가 존재하지 않습니다.");
        }
        else if (memberDAO.isExistMemberId(sellerId)==0) {
            throw new IllegalArgumentException("결제 처리에 실패했습니다. 해당하는 sellerId가 존재하지 않습니다.");
        }
        else if (itemService.isItemIdExist(itemId)==0) {
            throw new IllegalArgumentException("결제 처리에 실패했습니다. 해당하는 itemId가 존재하지 않습니다.");
        }
    }

    public OrderResponseDTO findOne(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 orderId를 가지는 주문내역이 존재하지 않습니다."));

        return new OrderResponseDTO(order);
    }

    public List<OrderResponseDTO> findByBuyerId(String buyerId, int page, int size) {
        List<Order> orders = orderRepository.findByBuyerId(buyerId, (page-1)*size, size);
        if (orders.isEmpty()){
            throw new IllegalArgumentException("해당 buyerId를 가지는 구매내역이 존재하지 않습니다.");
        }

        return orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    public List<OrderResponseDTO> findBySellerId(String sellerId, int page, int size) {
        List<Order> orders = orderRepository.findBySellerId(sellerId, (page-1)*size, size);
        if (orders.isEmpty()){
            throw new IllegalArgumentException("해당 sellerId를 가지는 판매내역이 존재하지 않습니다.");
        }

        return orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    public List<OrderResponseDTO> findByItemId(Long itemId, int page, int size) {
        List<Order> orders = orderRepository.findByItemId(itemId, (page-1)*size, size);
        if (orders.isEmpty()){
            throw new IllegalArgumentException("해당 itemId를 가지는 주문내역이 존재하지 않습니다.");
        }

        return orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    public List<OrderResponseDTO> findAll(int page, int size) {
        List<Order> orders = orderRepository.findAll((page-1)*size, size);
        if (orders.isEmpty()){
            throw new IllegalArgumentException("주문내역이 존재하지 않습니다.");
        }

        return orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    public void deleteOrder(Long orderId) {
        Optional<Order> canceledOrder = orderRepository.findByOrderId(orderId);

        if (canceledOrder.isPresent()) {
            Order order = canceledOrder.get();
            Long itemId = order.getItemId();
            int canceledQuantity = order.getCount();

            if (itemService.getStock(itemId) == 0) {
                itemService.updateIsSoldout(itemId);
            }

            itemService.updateStock(itemId, -canceledQuantity);
            itemService.updateCount(itemId, -canceledQuantity);
            orderRepository.cancel(orderId);
        } else {
            throw new IllegalStateException("해당 주문 ID: " + orderId + "가 존재하지 않습니다.");
        }
    };
}