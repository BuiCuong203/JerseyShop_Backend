package JerseyShop.JerseyShop.repository;

import JerseyShop.JerseyShop.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    boolean existsByNameType(String nameType);
}
