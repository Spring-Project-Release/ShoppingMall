package release.release_proj.repository;

import release.release_proj.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    public void save(Order order);
    public Optional<Order> findByOrderId(Long orderId);
    public Optional<List<Order>> findByBuyerId(String buyerId, int offset, int limit);
    public Optional<List<Order>> findBySellerId(String sellerId, int offset, int limit);
    public Optional<List<Order>> findByItemId(Long itemId, int offset, int limit);
    public Optional<List<Order>> findAll(int offset, int limit);
    public void cancel(Long orderId); //주문취소
}
