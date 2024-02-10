package release.release_proj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import release.release_proj.domain.MemberVO;
import release.release_proj.dto.OrderRequestDTO;
import release.release_proj.dto.OrderResponseDTO;
import release.release_proj.service.OrderService;

import java.util.List;

@Slf4j //log 사용
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> newOrder(@RequestBody OrderRequestDTO orderDTO, @SessionAttribute(name="userInfo", required = false) MemberVO loginMember) {
        try {
            if (loginMember == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 주문해주세요.");
            } else if (!orderDTO.getBuyerId().equals(loginMember.getMemberId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("주문자와 로그인한 유저 정보가 일치하지 않는 오류가 발생했습니다. 로그아웃 후 다시 로그인해주십시오.");
            } else {
                orderService.save(orderDTO);
                return ResponseEntity.ok("주문이 성공적으로 처리되었습니다.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 처리 중 오류가 발생했습니다.");
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> orderList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<OrderResponseDTO> orderDTOs = orderService.findAll(page, size);
            return ResponseEntity.ok(orderDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OrderResponseDTO>> memberPurchaseList(@PathVariable(name = "buyerId") String buyerId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<OrderResponseDTO> orderDTOs = orderService.findByBuyerId(buyerId, page, size);
            return ResponseEntity.ok(orderDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<OrderResponseDTO>> memberSaleList(@PathVariable(name = "sellerId") String sellerId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<OrderResponseDTO> orderDTOs = orderService.findBySellerId(sellerId, page, size);
            return ResponseEntity.ok(orderDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<OrderResponseDTO>> itemOrderList(@PathVariable(name = "itemId") Long itemId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            List<OrderResponseDTO> orderDTOs = orderService.findByItemId(itemId, page, size);
            return ResponseEntity.ok(orderDTOs);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> readOrder(@PathVariable(name = "orderId") Long orderId) {
        try {
            OrderResponseDTO orderDTO = orderService.findOne(orderId);
            return ResponseEntity.ok(orderDTO);
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
