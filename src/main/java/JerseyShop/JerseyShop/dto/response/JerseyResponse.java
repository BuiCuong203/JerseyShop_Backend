package JerseyShop.JerseyShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JerseyResponse {
    String name;
    String description;
    Double price;
    Double discount;
    String nameClub;
    Long nameType;
    Double rating;
    List<String> sizes;
    List<String> imageUrl;
}
