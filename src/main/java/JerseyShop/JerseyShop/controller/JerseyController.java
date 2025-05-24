package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.JerseyRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.JerseyResponse;
import JerseyShop.JerseyShop.model.User;
import JerseyShop.JerseyShop.service.JerseyService;
import JerseyShop.JerseyShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jerseys")
public class JerseyController {

    @Autowired
    private JerseyService jerseyService;

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ApiResponse<JerseyResponse> getJerseyById(@PathVariable Long id) throws Exception {
        JerseyResponse jerseyResponse = jerseyService.getJerseyById(id);

        return ApiResponse.<JerseyResponse>builder()
                .message("Jersey có id là " + id)
                .result(jerseyResponse)
                .build();
    }

    @GetMapping()
    public ApiResponse<List<JerseyResponse>> getAllJerseys() throws Exception {
        List<JerseyResponse> jerseyResponses = jerseyService.getAllJerseys();

        return ApiResponse.<List<JerseyResponse>>builder()
                .message("Danh sách Jersey")
                .result(jerseyResponses)
                .build();
    }

    @PostMapping("/{id}/addToFavourites")
    public ApiResponse<JerseyResponse> addToFavourites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        JerseyResponse jerseyResponse = jerseyService.addToFavourites(id, user.getId());

        return ApiResponse.<JerseyResponse>builder()
                .message("Thêm/Xóa Jersey yêu thích thành công")
                .result(jerseyResponse)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<JerseyResponse>> searchJersey(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<JerseyResponse> jerseyResponses = jerseyService.searchJersey(keyword);

        return ApiResponse.<List<JerseyResponse>>builder()
                .message("Danh sách Jersey tìm kiếm theo từ khóa: " + keyword)
                .result(jerseyResponses)
                .build();
    }
}
