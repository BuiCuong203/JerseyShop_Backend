package JerseyShop.JerseyShop.dto.request;

import JerseyShop.JerseyShop.model.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    String fullname;
    String email;
    String phone;
    String province;
    String district;
    String ward;
    String streetName;
    String methodPayment;
}
