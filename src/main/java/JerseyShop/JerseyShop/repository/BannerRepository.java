package JerseyShop.JerseyShop.repository;

import JerseyShop.JerseyShop.model.Banners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banners, Long> {
}
