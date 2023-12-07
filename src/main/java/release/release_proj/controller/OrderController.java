package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.Order;
import release.release_proj.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> newOrder(@RequestBody Order order) {
        try {
            orderService.save(order);
            return ResponseEntity.ok("주문이 성공적으로 처리되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 처리 중 오류가 발생했습니다.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> orderList() {
        Optional<List<Order>> orders = orderService.findAll();

        if (orders.isPresent()){
            return ResponseEntity.ok(orders.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<Order>> memberOrderList(@PathVariable(name="memberId") String memberId) {
        Optional<List<Order>> orders = orderService.findByMemberId(memberId);

        if (orders.isPresent()){
            return ResponseEntity.ok(orders.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("item/{itemId}")
    public ResponseEntity<List<Order>> itemOrderList(@PathVariable(name="itemId") Long itemId) {
        Optional<List<Order>> orders = orderService.findByItemId(itemId);

        if (orders.isPresent()){
            return ResponseEntity.ok(orders.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> readOrder(@PathVariable(name="orderId") Long orderId) {
        Optional<Order> order = orderService.findOne(orderId);

        if (order.isPresent()){
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable(name="orderId") Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("주문이 성공적으로 삭제되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 삭제 중 오류가 발생했습니다.");
        }
    }

}
