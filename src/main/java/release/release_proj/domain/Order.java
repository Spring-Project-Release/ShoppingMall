package release.release_proj.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor //entity(domain)에는 생성자 작성해야 함
@Getter
@Setter //dto 적용 시 setter 생략하기
@Entity
@EntityListeners(AuditingEntityListener.class) //orderDate 자동생성 위함
@Table(name = "orders") //order: 예약어 -> table 이름으로 사용 불가
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long orderId;
    private String memberId;  //ManyTonOne Mapping 등 이용할건지 생각해보기(그러면 db 자동생성해서 외래키 예외처리할 필요x, itemService.getAmount, getPrice 함수 만들 필요 x)
    private Long itemId;
    @CreatedDate
    private LocalDateTime orderDate;
    private int price; //총 가격
    private int count; //구매 개수
    private String memo;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", itemId=" + itemId + '\'' +
                ", orderDate=" + orderDate + '\'' +
                ", price=" + price + '\'' +
                ", count=" + count + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
