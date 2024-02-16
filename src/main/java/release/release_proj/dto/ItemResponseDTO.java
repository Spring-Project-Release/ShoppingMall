package release.release_proj.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import release.release_proj.domain.Item;

import java.time.LocalDateTime;

@Data
public class ItemResponseDTO {

    @NotNull
    private Long itemId;
    @NotNull
    private String name;
    @NotNull
    private int price;
    @NotNull
    private int count;
    @NotNull
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
    private LocalDateTime createdAt;

    public ItemResponseDTO(Item item) {
        this.itemId = item.getItemId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.count = item.getCount();
        this.stock = item.getStock();
        this.category = item.getCategory();
        this.text = item.getText();
        this.isSoldout = item.getIsSoldout();
        this.picture = item.getPicture();
        this.discount = item.getDiscount();
        this.origin = item.getOrigin();
        this.deliveryType = item.getDeliveryType();
        this.sellerId = item.getSellerId();
        this.unit = item.getUnit();
        this.createdAt = item.getCreatedAt();
    }
}
