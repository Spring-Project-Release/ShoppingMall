package release.release_proj.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import release.release_proj.domain.Item;

@Data
@Builder
public class ItemRequestDTO {

    @NotNull
    private String name;
    @NotNull
    private int price;
    @NotNull
    private int count;
    private int stock;
    private String category;
    private String text;
    @NotNull
    private Boolean isSoldout;
    private String picture;
    private float discount;
    private String origin;
    private int deliveryType;
    @NotNull
    private String sellerId;
    private String unit;

    public Item toEntity() {
        return Item.builder()
                .name(name)
                .price(price)
                .count(count)
                .stock(stock)
                .category(category)
                .text(text)
                .isSoldout(isSoldout)
                .picture(picture)
                .discount(discount)
                .origin(origin)
                .deliveryType(deliveryType)
                .sellerId(sellerId)
                .unit(unit)
                .build();
    }

}
