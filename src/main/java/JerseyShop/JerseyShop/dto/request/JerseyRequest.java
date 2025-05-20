package JerseyShop.JerseyShop.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JerseyRequest {
    String name;
    String description;
    Double price;
    Double discount;
    Long clubId;
    Long typeId;
    String sizes;
    List<String> imageUrl;
}
