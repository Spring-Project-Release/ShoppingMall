package release.release_proj.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import release.release_proj.domain.Order;

import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {

    @NotNull
    private Long orderId;
    @NotNull
    private String buyerId;
    @NotNull
    private String sellerId;
    @NotNull
    private Long itemId;
    @CreatedDate
    private LocalDateTime orderDate;
    @NotNull
    private int price;
    @NotNull
    private int count;
    private String memo;

    public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        this.buyerId = order.getBuyerId();
        this.sellerId = order.getSellerId();
        this.itemId = order.getItemId();
        this.orderDate = order.getOrderDate();
        this.price = order.getPrice();
        this.count = order.getCount();
        this.memo = order.getMemo();
    }
}
