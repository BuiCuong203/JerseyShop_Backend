package JerseyShop.JerseyShop.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddCartItemRequest {
    Long jerseyId;
    Long sizeId;
    int quantity;
}
