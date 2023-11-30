package release.release_proj.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor //entity(domain)에는 생성자 작성해야 함
@Getter
@Setter //dto 적용 시 setter 생략하기
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long orderId;
    private String memberId;
    private Long itemId;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate orderDate;
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
