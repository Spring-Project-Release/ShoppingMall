package release.release_proj.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import release.release_proj.domain.Cart;

@Data
@Builder
public class CartRequestDTO {

    @NotNull
    private String memberId;
    @NotNull
    private Long itemId;
    @NotNull
    private int amount;

    public Cart toEntity() {
        return Cart.builder()
                .memberId(memberId)
                .itemId(itemId)
                .amount(amount)
                .build();
    }
}