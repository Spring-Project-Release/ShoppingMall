package release.release_proj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import release.release_proj.domain.Item;

@Data
@Builder
@AllArgsConstructor//
@NoArgsConstructor//
public class ItemRequestDTO {

    private Long itemId;
    //@NotNull
    private String name;
    //@NotNull
    private int price;
    //@NotNull
    private int count;
    //@NotNull
    private int stock;
    private String category;
    private String text;
    //@NotNull
    private Boolean isSoldout;
    private String picture;
    private float discount;
    private String origin;
    private int deliveryType;
    //@NotNull
    private String sellerId;
    private String unit;

    public Item toEntity() {
        return Item.builder()
                .itemId(itemId)
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
