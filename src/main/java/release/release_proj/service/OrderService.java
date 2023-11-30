package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.domain.Order;
import release.release_proj.repository.ItemRepository;
import release.release_proj.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public void save(Order order){
        //item.stock가 order.count 비교: stock 수 < count 수이면 error메시지 띄우기
        int currentStock = itemRepository.getStock(order.getItemId());
        if (currentStock < order.getCount()) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        orderRepository.save(order);
        itemRepository.updateStock(order.getItemId(), order.getCount());

        if (currentStock - order.getCount() == 0) { //재고가 0인 경우
            itemRepository.updateIsSoldout(order.getItemId());
        }
    };

}

//장바구니 결제는 cartservice에 따로 구현