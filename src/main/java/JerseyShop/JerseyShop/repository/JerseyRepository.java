package JerseyShop.JerseyShop.repository;

import JerseyShop.JerseyShop.model.Jersey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JerseyRepository extends JpaRepository<Jersey, Long> {
    boolean existsByName(String name);

    @Query("SELECT j FROM Jersey j WHERE lower(j.name) LIKE lower(concat('%',:keyword,'%')) " +
            "OR lower(j.club.nameClub) LIKE lower(concat('%',:keyword,'%')) " +
            "OR lower(j.type.nameType) LIKE lower(concat('%',:keyword,'%'))")
    List<Jersey> searchJersey(@Param("keyword") String keyword);
}
