package release.release_proj.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import release.release_proj.domain.Order;

@Data
@Builder
public class OrderRequestDTO {

    @NotNull
    private String buyerId;
    @NotNull
    private String sellerId;
    @NotNull
    private Long itemId;
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
                .price(price)
                .count(count)
                .memo(memo)
                .build();
    }
}