package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.SigninRequest;
import JerseyShop.JerseyShop.dto.request.SignupRequest;
import JerseyShop.JerseyShop.dto.response.AuthResponse;

public interface AuthService {
    public AuthResponse signup(SignupRequest signupRequest);

    public AuthResponse signin(SigninRequest signinRequest);

    public void signout(String jwt);
}
