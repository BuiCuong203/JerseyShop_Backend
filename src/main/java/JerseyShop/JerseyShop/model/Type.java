package JerseyShop.JerseyShop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String nameType;

    @OneToMany(mappedBy = "type")
    List<Jersey> jerseys;
}
