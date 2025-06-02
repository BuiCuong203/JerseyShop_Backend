package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.UpdateProfileRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.ProfileResponse;
import JerseyShop.JerseyShop.model.User;
import JerseyShop.JerseyShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ApiResponse<ProfileResponse> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        ProfileResponse profileResponse = userService.getUserProfile(user);

        return ApiResponse.<ProfileResponse>builder()
                .message("Thông tin người dùng")
                .result(profileResponse)
                .build();
    }

    @PutMapping("/updateProfile")
    public ApiResponse<ProfileResponse> updateProfile(
            @RequestHeader("Authorization") String jwt,
            @RequestBody UpdateProfileRequest profileRequest)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        ProfileResponse profileResponse = userService.updateUserProfile(user, profileRequest);

        return ApiResponse.<ProfileResponse>builder()
                .message("Cập nhật thành công")
                .result(profileResponse)
                .build();
    }
}
