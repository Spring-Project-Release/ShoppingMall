package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Order;
import release.release_proj.repository.MemberDAO;
import release.release_proj.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final MemberDAO memberDAO;

    public void save(Order order) {
        validateMemberAndItemExistence(order.getMemberId(), order.getItemId());
        //item.stock과 order.count 비교: stock 수 < count 수이면 error 메시지 띄우기
        int currentStock = itemService.getStock(order.getItemId());
        if (currentStock < order.getCount()) {
            throw new IllegalStateException(order.getItemId()+" 상품의 재고가 부족합니다.");
        }
        orderRepository.save(order);
        itemService.updateStock(order.getItemId(), order.getCount()); //item 재고 감소
        itemService.updateCount(order.getItemId(), order.getCount()); //item 판매개수 상승

        if (currentStock - order.getCount() == 0) { //재고가 0인 경우
            itemService.updateIsSoldout(order.getItemId());
        }
    };

    private void validateMemberAndItemExistence(String memberId, Long itemId) {
        // memberId와 itemId가 존재하는지 여부 확인
        if (memberDAO.isExistMemberId(memberId)==0 && itemService.isItemIdExist(itemId)==0) {
            throw new IllegalArgumentException("결제 처리에 실패했습니다. 해당하는 memberId와 itemId가 존재하지 않습니다.");
        }
        else if (memberDAO.isExistMemberId(memberId)==0) {
            throw new IllegalArgumentException("결제 처리에 실패했습니다. 해당하는 memberId가 존재하지 않습니다.");
        }
        else if (itemService.isItemIdExist(itemId)==0) {
            throw new IllegalArgumentException("결제 처리에 실패했습니다. 해당하는 itemId가 존재하지 않습니다.");
        }
    }

    public Order findOne(Long orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 orderId를 가지는 주문내역이 존재하지 않습니다."));
    }

    public List<Order> findByMemberId(String memberId) {
        Optional<List<Order>> orders = orderRepository.findByMemberId(memberId);
        if (orders.isEmpty() || orders.get().isEmpty()){
            throw new IllegalArgumentException("해당 memberId를 가지는 주문내역이 존재하지 않습니다.");
        }

        return orders.get();
    }

    public List<Order> findByItemId(Long itemId) {
        Optional<List<Order>> orders = orderRepository.findByItemId(itemId);
        if (orders.isEmpty() || orders.get().isEmpty()){
            throw new IllegalArgumentException("해당 itemId를 가지는 주문내역이 존재하지 않습니다.");
        }

        return orders.get();
    }

    public List<Order> findAll() {
        Optional<List<Order>> orders = orderRepository.findAll();
        if (orders.isEmpty() || orders.get().isEmpty()){
            throw new IllegalArgumentException("주문내역이 존재하지 않습니다.");
        }

        return orders.get();
    }

    public void deleteOrder(Long orderId) {
        //itemRepository.findByItemId로 domain을 가져오고 domain 안에서 아예 updateStock, updateIsSoldout까지 정의하는 게 나을수도?
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