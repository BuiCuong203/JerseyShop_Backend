package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.model.Banners;
import JerseyShop.JerseyShop.service.BannersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannersService bannersService;

    @GetMapping("{id}")
    public ApiResponse<Banners> getBannerById(@PathVariable Long id) throws Exception {
        Banners banner = bannersService.getBannerById(id);

        return ApiResponse.<Banners>builder()
                .message("Thông tin Club mã " + id)
                .result(banner)
                .build();
    }

    @GetMapping()
    public ApiResponse<List<Banners>> getAllBanners(){
        List<Banners> bannersList = bannersService.getListBanner();

        return ApiResponse.<List<Banners>>builder()
                .message("Danh sách Banners")
                .result(bannersList)
                .build();
    }
}
