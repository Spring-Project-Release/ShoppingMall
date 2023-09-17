package release.release_proj.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Builder
@Getter
@Setter
@Entity
public class Cart {

    private Long user_id;
    private String item_name;
    private int count;
}
