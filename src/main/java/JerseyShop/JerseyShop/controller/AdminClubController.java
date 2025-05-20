package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.ClubRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.ClubResponse;
import JerseyShop.JerseyShop.service.ClubService;
import JerseyShop.JerseyShop.service.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/clubs")
public class AdminClubController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ClubService clubService;

    @PostMapping()
    public ApiResponse<ClubResponse> createClub(
            @RequestPart("clubRequest") String clubRequestJson,
            @RequestPart("imageFile") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClubRequest clubRequest = objectMapper.readValue(clubRequestJson, ClubRequest.class);

        String imagePath = fileStorageService.storeFile(imageFile);
        clubRequest.setImage(imagePath);
        ClubResponse clubResponse = clubService.createClub(clubRequest);

        return ApiResponse.<ClubResponse>builder()
                .message("Tạo Club mới thành công")
                .result(clubResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ClubResponse> updateClub(
            @RequestPart("clubRequest") String clubRequestJson,
            @RequestPart("imageFile") MultipartFile imageFile,
            @PathVariable Long id)
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClubRequest clubRequest = objectMapper.readValue(clubRequestJson, ClubRequest.class);
        String imagePath = fileStorageService.storeFile(imageFile);
        clubRequest.setImage(imagePath);
        ClubResponse clubResponse = clubService.updateClub(id, clubRequest);

        return ApiResponse.<ClubResponse>builder()
                .message("Cập nhật Club thành công")
                .result(clubResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteClub(@PathVariable Long id){
        clubService.deleteClub(id);

        return ApiResponse.<Void>builder()
                .message("Xóa Club thành công")
                .build();
    }
}
