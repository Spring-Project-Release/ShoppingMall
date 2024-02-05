package release.release_proj.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponseDTO {

    @NotNull
    Long OrderId;
    @NotNull
    private String buyerId;
    @NotNull
    private String sellerId;
    @NotNull
    private Long itemId;
    @CreatedDate
    private LocalDateTime orderDate;
    @NotNull
    private int price;
    @NotNull
    private int count;
    private String memo;
}
