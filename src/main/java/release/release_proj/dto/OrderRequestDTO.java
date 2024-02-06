package release.release_proj.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import release.release_proj.domain.Order;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderRequestDTO {

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

    public Order toEntity() {
        return Order.builder()
                .buyerId(buyerId)
                .sellerId(sellerId)
                .itemId(itemId)
                .orderDate(orderDate != null ? orderDate : LocalDateTime.now())
                .price(price)
                .count(count)
                .memo(memo)
                .build();
    }
}