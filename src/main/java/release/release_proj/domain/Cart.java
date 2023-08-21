package release.release_proj.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

    private Long user_id;
    private String item_name;
    private int count;
}
