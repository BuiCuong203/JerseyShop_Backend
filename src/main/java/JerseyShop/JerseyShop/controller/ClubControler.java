package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.ClubRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.ClubResponse;
import JerseyShop.JerseyShop.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubControler {

    @Autowired
    private ClubService clubService;

    @GetMapping("/{id}")
    public ApiResponse<ClubResponse> getClubById(@PathVariable Long id) throws Exception {
        ClubResponse clubResponse = clubService.getClubById(id);

        return ApiResponse.<ClubResponse>builder()
                .message("Thông tin Club mã " + id)
                .result(clubResponse)
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ClubResponse>> getAllClub() throws Exception {
        List<ClubResponse> clubResponseList = clubService.getListClub();

        return ApiResponse.<List<ClubResponse>>builder()
                .message("Danh sách Club")
                .result(clubResponseList)
                .build();
    }
}
