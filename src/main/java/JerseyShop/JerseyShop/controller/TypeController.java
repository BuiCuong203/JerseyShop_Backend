package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.TypeResponse;
import JerseyShop.JerseyShop.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/{id}")
    public ApiResponse<TypeResponse> getClubById(@PathVariable Long id) throws Exception {
        TypeResponse clubResponse = typeService.getTypeById(id);

        return ApiResponse.<TypeResponse>builder()
                .message("Thông tin Type mã " + id)
                .result(clubResponse)
                .build();
    }

    @GetMapping()
    public ApiResponse<List<TypeResponse>> getAllClub() throws Exception {
        List<TypeResponse> clubResponseList = typeService.getListType();

        return ApiResponse.<List<TypeResponse>>builder()
                .message("Danh sách Type")
                .result(clubResponseList)
                .build();
    }
}
