package JerseyShop.JerseyShop.dto.response;

import JerseyShop.JerseyShop.model.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {
    Long id;

    String fullname;

    String email;

    String phone;

    String role;

    Address address;
}
