package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.model.Banners;
import JerseyShop.JerseyShop.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannersServiceImp implements BannersService{

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banners createBanner(String image) {
        Banners createBanber = Banners.builder()
                .image(image)
                .build();
        Banners saveBanner = bannerRepository.save(createBanber);

        return saveBanner;
    }

    @Override
    public Banners updateBanner(Long id, String image) throws Exception {
        Banners banner = bannerRepository.findById(id).orElse(null);
        if(banner == null){
            throw new Exception("Banner not found");
        }

        banner.setImage(image);
        Banners saveBanner = bannerRepository.save(banner);

        return saveBanner;
    }

    @Override
    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }

    @Override
    public Banners getBannerById(Long id) throws Exception {
        Banners banner = bannerRepository.findById(id).orElse(null);
        if(banner == null){
            throw new Exception("Banner not found");
        }
        return banner;
    }

    @Override
    public List<Banners> getListBanner() {
        return bannerRepository.findAll();
    }
}
