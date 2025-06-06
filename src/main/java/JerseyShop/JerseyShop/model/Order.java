package JerseyShop.JerseyShop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true)
    String orderCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    User customer;

    String fullname;

    String email;

    String phone;

    @Embedded
    Address deliveryAddress;

    Date createdAt;

    String orderStatus;

    String methodPayment;

    boolean paymentStatus;

    int totalItem;

    Long totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> items = new ArrayList<>();
}
