package JerseyShop.JerseyShop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Jersey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String description;

    @Column(length = 1000)
    @ElementCollection
    List<String> images;

    Double price;

    Double discount;

    @ManyToOne
    @JoinColumn(name = "club_id")
    Club club;

    @ManyToOne
    @JoinColumn(name = "type_id")
    Type type;

    @OneToMany(mappedBy = "jersey", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Size> sizes;

    Double rating;
}
