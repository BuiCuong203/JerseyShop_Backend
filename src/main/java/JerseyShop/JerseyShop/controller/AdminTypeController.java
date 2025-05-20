package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.TypeRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.TypeResponse;
import JerseyShop.JerseyShop.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/types")
public class AdminTypeController {

    @Autowired
    private TypeService typeService;

    @PostMapping()
    public ApiResponse<TypeResponse> createType(@RequestBody TypeRequest typeRequest){
        TypeResponse typeResponse = typeService.createType(typeRequest);

        return ApiResponse.<TypeResponse>builder()
                .message("Tạo Type mới thành công")
                .result(typeResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TypeResponse> updateClub(
            @RequestBody TypeRequest typeRequest,
            @PathVariable Long id)
            throws Exception {
        TypeResponse clubResponse = typeService.updateType(id, typeRequest);

        return ApiResponse.<TypeResponse>builder()
                .message("Cập nhật Type thành công")
                .result(clubResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteClub(@PathVariable Long id){
        typeService.deleteType(id);

        return ApiResponse.<Void>builder()
                .message("Xóa Type thành công")
                .build();
    }
}
