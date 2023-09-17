package release.release_proj.dto;

import com.sun.istack.NotNull;

public class ItemDto {
    @NotNull
    private Long id;

    @NotNull
    private String name; //상품 이름

    @NotNull
    private int price;

    private int count; //판매 개수

    private int stock; //재고 수

    @NotNull
    private String item_type; //상품 종류

    private String text; //상품에 대한 상세설명

    @NotNull
    private boolean isSoldout; //상품 상태: 품절 / 판매중

    private String picture; //상품 사진
}
