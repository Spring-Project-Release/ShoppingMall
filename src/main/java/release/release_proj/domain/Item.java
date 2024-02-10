package release.release_proj.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor //entity(domain)에는 생성자 작성해야 함
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class) //Date 자동생성 위함
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    @Column(name = "item_id")
    private Long itemId;
    private String name; //상품 이름
    private int price;
    private int count; //판매 개수
    private int stock; //재고 수
    private String category; //상품 종류
    private String text; //상품에 대한 상세설명
    @Column(name= "is_soldout")
    private Boolean isSoldout; //상품 상태: 품절 / 판매중
    private String picture; //상품 사진 url
    private float discount; //할인률
    private String origin; //원산지
    @Column(name= "delivery_type")
    private int deliveryType; //배송타입
    @Column(name = "seller_id")
    private String sellerId; //판매자 유저 아이디
    private String unit; //판매단위
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
