package JerseyShop.JerseyShop.repository;

import JerseyShop.JerseyShop.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    boolean existsByNameClub(String nameClub);
}
