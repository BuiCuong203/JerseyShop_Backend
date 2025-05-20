package JerseyShop.JerseyShop.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
    String fullname;
    String email;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;
}
