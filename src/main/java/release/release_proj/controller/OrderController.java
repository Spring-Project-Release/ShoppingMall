package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.Order;
import release.release_proj.service.OrderService;

import java.util.List;

@Slf4j //log 사용
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> newOrder(@RequestBody Order order) {
        try {
            orderService.save(order);
            return ResponseEntity.ok("주문이 성공적으로 처리되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 처리 중 오류가 발생했습니다.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> orderList() {
        try {
            List<Order> orders = orderService.findAll();
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Order>> memberPurchaseList(@PathVariable(name = "buyerId") String buyerId) {
        try {
            List<Order> orders = orderService.findByBuyerId(buyerId);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Order>> memberSaleList(@PathVariable(name = "sellerId") String sellerId) {
        try {
            List<Order> orders = orderService.findBySellerId(sellerId);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Order>> itemOrderList(@PathVariable(name = "itemId") Long itemId) {
        try {
            List<Order> orders = orderService.findByItemId(itemId);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> readOrder(@PathVariable(name = "orderId") Long orderId) {
        try {
            Order order = orderService.findOne(orderId);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable(name="orderId") Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("주문이 성공적으로 취소되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 취소 중 오류가 발생했습니다.");
        }
    }

}
