package release.release_proj.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor //entity(domain)에는 생성자 작성해야 함
@Getter
@Setter //dto 적용 시 setter 생략하기
@Entity
@Table(name = "orders") //order: 예약어 -> table 이름으로 사용 불가
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long orderId;
    private String memberId;  //ManyTonOne Mapping 등 이용할건지 생각해보기(그러면 외래키 예외처리할 필요x, getAmount 등의 함수 만들 필요 x)
    private Long itemId;
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
