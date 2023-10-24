package release.release_proj.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor //entity(domain)에는 생성자 작성해야 함
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cartId")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="itemId")
    private Item item;

    private int count; //장바구니에 담긴 해당 item의 개수
}
