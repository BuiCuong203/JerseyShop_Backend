package JerseyShop.JerseyShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Long id;
    int totalItems;
    Long totalPrice;
    List<CartItemResponse> cartItems;
}
