package release.release_proj.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponseDTO {

    @NotNull
    private Long cartId;
    @NotNull
    private String memberId;
    @NotNull
    private Long itemId;
    @NotNull
    private int amount;

    /*public CartResponseDTO(Cart cart) {
        this.cartId = cart.getCartId();
        this.memberId = cart.getMemberId();
        this.itemId = cart.getItemId();
        this.amount = cart.getAmount();
    }*/
}
