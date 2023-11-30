package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Order;
import release.release_proj.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    public void save(Order order) {
        //item.stock가 order.count 비교: stock 수 < count 수이면 error 메시지 띄우기
        int currentStock = itemService.getStock(order.getItemId());
        if (currentStock < order.getCount()) {
            throw new IllegalStateException("해당 상품의 재고가 부족합니다.");
        }

        orderRepository.save(order);
        itemService.updateStock(order.getItemId(), order.getCount());

        if (currentStock - order.getCount() == 0) { //재고가 0인 경우
            itemService.updateIsSoldout(order.getItemId());
        }
    };

    public Optional<Order> readOrder(Long orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    public Optional<List<Order>> findByMemberId(String memberId) {
        return orderRepository.findByMemberId(memberId);
}

    public Optional<List<Order>> findByItemId(Long itemId) {
        return orderRepository.findByItemId(itemId);
    }

    public Optional<List<Order>> findAll() {
        return orderRepository.findAll();
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
            orderRepository.cancel(orderId);
        } else {
            throw new IllegalStateException("해당 주문을 찾을 수 없습니다. 주문 ID: " + orderId);
        }
    };



}

//장바구니 결제는 cartservice에 따로 구현