package JerseyShop.JerseyShop.repository;

import JerseyShop.JerseyShop.model.Jersey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JerseyRepository extends JpaRepository<Jersey, Long> {
}
