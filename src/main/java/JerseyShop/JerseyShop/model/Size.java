package JerseyShop.JerseyShop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String size;

    Long quantity;

    @ManyToOne
    @JoinColumn(name = "jersey_id")
    Jersey jersey;
}
