package JerseyShop.JerseyShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Long id;
    String nameJersey;
    String nameClub;
    String nameType;
    String image;
    String size;
    int quantity;
    Long totalPrice;
}
