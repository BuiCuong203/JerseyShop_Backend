package JerseyShop.JerseyShop.repository;

import JerseyShop.JerseyShop.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
}
