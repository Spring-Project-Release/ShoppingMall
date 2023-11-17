package release.release_proj.domain;

import lombok.*;

import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor //entity(domain)에는 생성자 작성해야 함
@Getter
@Setter
public class Cart {

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long cartId;

    private int count; //장바구니에 담긴 총 상품 개수

    //장바구니의 상품 종류 개수도 count해야 할까..? 아니면 repository에서 추가?

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

    public static Cart createCart(User user) { //팩토리 메서드
        Cart cart = new Cart();
        cart.user = user;
        cart.count = 0;

        return cart;
    }
    */
    
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment: JPA서 사용
    private Long cartId;
    private String memberId;
    private Long itemId;
    private int amount; //cart에 담긴 해당 item의 개수: 0이 아님

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", memberId='" + memberId + '\'' +
                ", itemId=" + itemId +
                ", amount=" + amount +
                '}';
    }
}