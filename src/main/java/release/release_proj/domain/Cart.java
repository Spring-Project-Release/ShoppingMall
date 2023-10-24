package release.release_proj.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor //entity(domain)에는 생성자 작성해야 함
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long cartId;

    private int count; //장바구니에 담긴 총 상품 개수

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;

    @OneToMany(mappedBy = "cart") //연관관계의 주인: cart임
    private List<CartItem> cartItems = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDate.now();
    }
}