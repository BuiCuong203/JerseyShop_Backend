package JerseyShop.JerseyShop.dto.response;

import JerseyShop.JerseyShop.model.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    String orderCode;
    String fullname;
    String email;
    String phone;
    Address deliveryAddress;
    Date createdAt;
    String orderStatus;
    String methodPayment;
    boolean paymentStatus;
    int totalItem;
    Long totalPrice;
    List<OrderItemResponse> items;
}
