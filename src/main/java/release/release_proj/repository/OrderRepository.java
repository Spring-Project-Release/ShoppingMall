package release.release_proj.repository;

import release.release_proj.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    public void save(Order order);
    public Optional<Order> findByOrderId(Long orderId);
    public Optional<List<Order>> findByMemberId(String memberId);
    public Optional<List<Order>> findByItemId(Long itemId);
    public Optional<List<Order>> findAll();
    public void cancelOrder(Long orderId); //주문취소
}
