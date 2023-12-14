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
        //try {
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
        /*} catch (DataIntegrityViolationException e) {
            // 외래 키 제약 조건 위배로 인한 예외 처리
            throw new IllegalArgumentException("Failed to make order. 해당하는 itemId나 memberId가 존재하지 않습니다.", e);
        }*/
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

    public Optional<Order> findOne(Long orderId) {
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
            itemService.updateStock(order.getItemId(), -canceledQuantity);
            orderRepository.cancel(orderId);
        } else {
            throw new IllegalStateException("해당 주문 ID: " + orderId + "가 존재하지 않습니다.");
        }
    };
}