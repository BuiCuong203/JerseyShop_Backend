package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.JerseyRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.JerseyResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.service.FileStorageService;
import JerseyShop.JerseyShop.service.JerseyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/jerseys")
public class AdminJerseyController {

    @Autowired
    private JerseyService jerseyService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping()
    public ApiResponse<JerseyResponse> createJersey(
            @RequestPart("jerseyRequest") String jerseyRequestJson,
            @RequestPart("sizesRequest") String sizesRequestJson,
            @RequestPart("imageFiles") List<MultipartFile> imageFiles)
            throws Exception {
        if (imageFiles == null || imageFiles.isEmpty() || imageFiles.stream().anyMatch(MultipartFile::isEmpty)) {
            throw new AppException(ErrorCode.INVALID_FILE);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JerseyRequest jerseyRequest = objectMapper.readValue(jerseyRequestJson, JerseyRequest.class);
        List<Long> sizeRequestList = objectMapper.readValue(sizesRequestJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Long.class));

        if(jerseyRequest.getSizes().trim().split(",").length != sizeRequestList.size() || sizeRequestList.stream().anyMatch(size -> size == null)){
            throw new AppException(ErrorCode.WRONG_QUANTITY);
        }

        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            String imagePath = fileStorageService.storeFile(imageFile);
            imagePaths.add(imagePath);
        }
        jerseyRequest.setImageUrl(imagePaths);

        JerseyResponse jerseyResponse = jerseyService.createJersey(jerseyRequest, sizeRequestList);

        return ApiResponse.<JerseyResponse>builder()
                .message("Tạo Jersey mới thành công")
                .result(jerseyResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<JerseyResponse> updateJersey(
            @PathVariable Long id,
            @RequestPart("jerseyRequest") String jerseyRequestJson,
            @RequestPart("sizesRequest") String sizesRequestJson,
            @RequestPart("imageFiles") List<MultipartFile> imageFiles)
            throws Exception {
        if (imageFiles == null || imageFiles.isEmpty() || imageFiles.stream().anyMatch(MultipartFile::isEmpty)) {
            throw new AppException(ErrorCode.INVALID_FILE);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JerseyRequest jerseyRequest = objectMapper.readValue(jerseyRequestJson, JerseyRequest.class);
        List<Long> sizeRequestList = objectMapper.readValue(sizesRequestJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Long.class));

        if(jerseyRequest.getSizes().trim().split(",").length != sizeRequestList.size() || sizeRequestList.stream().anyMatch(size -> size == null)){
            throw new AppException(ErrorCode.WRONG_QUANTITY);
        }

        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            String imagePath = fileStorageService.storeFile(imageFile);
            imagePaths.add(imagePath);
        }
        jerseyRequest.setImageUrl(imagePaths);

        JerseyResponse jerseyResponse = jerseyService.updateJersey(id, jerseyRequest, sizeRequestList);

        return ApiResponse.<JerseyResponse>builder()
                .message("Cập nhật Jersey thành công")
                .result(jerseyResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteJersey(@PathVariable Long id) throws Exception {
        jerseyService.deleteJersey(id);
        return ApiResponse.<String>builder()
                .message("Xóa Jersey thành công")
                .build();
    }
}
