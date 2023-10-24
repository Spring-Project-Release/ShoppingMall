package release.release_proj.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor //entity(domain)에는 생성자 작성해야 함
@Getter
@Setter //dto 적용 시 setter 생략하기
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long itemId;
    private String name; //상품 이름
    private int price;
    private int count; //판매 개수
    private int stock; //재고 수
    private String item_type; //상품 종류
    private String text; //상품에 대한 상세설명
    private boolean isSoldout; //상품 상태: 품절 / 판매중
    private String picture; //상품 사진

}
