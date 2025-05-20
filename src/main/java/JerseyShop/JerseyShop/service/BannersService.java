package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.model.Banners;

import java.util.List;

public interface BannersService {
    public Banners createBanner(String image);

    public Banners updateBanner(Long id, String image) throws Exception;

    public void deleteBanner(Long id);

    public Banners getBannerById(Long id) throws Exception;

    public List<Banners> getListBanner();
}
