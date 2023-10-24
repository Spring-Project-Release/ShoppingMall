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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long userId;
}
