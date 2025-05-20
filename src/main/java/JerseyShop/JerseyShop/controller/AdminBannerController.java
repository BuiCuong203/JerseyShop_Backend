package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.model.Banners;
import JerseyShop.JerseyShop.service.BannersService;
import JerseyShop.JerseyShop.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/banners")
public class AdminBannerController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private BannersService bannersService;

    @PostMapping()
    public ApiResponse<Banners> createBanner(@RequestPart("imageFile") MultipartFile imageFile){
        String imagePath = fileStorageService.storeFile(imageFile);

        Banners createBanner = bannersService.createBanner(imagePath);

        return ApiResponse.<Banners>builder()
                .message("Tạo Banner mới thành công")
                .result(createBanner)
                .build();
    }

    @PutMapping("{id}")
    public ApiResponse<Banners> updateBanner(
            @RequestPart("imageFile") MultipartFile imageFile,
            @PathVariable Long id) throws Exception {
        String imagePath = fileStorageService.storeFile(imageFile);

        Banners updateBanner = bannersService.updateBanner(id, imagePath);

        return ApiResponse.<Banners>builder()
                .message("Cập nhật Banner thành công")
                .result(updateBanner)
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Void> deleteBanner(@PathVariable Long id){
        bannersService.deleteBanner(id);

        return ApiResponse.<Void>builder()
                .message("Xóa Banner thành công")
                .build();
    }
}
