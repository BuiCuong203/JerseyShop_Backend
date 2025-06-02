package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.SigninRequest;
import JerseyShop.JerseyShop.dto.request.SignupRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.AuthResponse;
import JerseyShop.JerseyShop.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<AuthResponse> signup(@RequestBody @Valid SignupRequest signupRequest) throws Exception {
        AuthResponse authResponse = authService.signup(signupRequest);

        return ApiResponse.<AuthResponse>builder()
                .message("Đăng ký thành công")
                .result(authResponse)
                .build();
    }

    @PostMapping("/signin")
    public ApiResponse<AuthResponse> signin(@RequestBody SigninRequest signinRequest){
        AuthResponse authResponse = authService.signin(signinRequest);

        return ApiResponse.<AuthResponse>builder()
                .message("Đăng nhập thành công")
                .result(authResponse)
                .build();
    }

    @PostMapping("/signout")
    public  ApiResponse<Void> signout(@RequestHeader("Authorization") String jwt){
        authService.signout(jwt);

        return ApiResponse.<Void>builder()
                .message("Đăng xuất thành công")
                .build();
    }
}
