package JerseyShop.JerseyShop.dto.response;

import JerseyShop.JerseyShop.model.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JerseyResponse {
    Long id;
    String name;
    String description;
    Long price;
    Long discount;
    String nameClub;
    String nameType;
    Double rating;
    List<Size> sizes;
    List<String> imageUrl;
}
