package JerseyShop.JerseyShop.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class updateProfileRequest {
    String fullname;

    String phone;

    String province;

    String district;

    String ward;

    String streetName;
}
