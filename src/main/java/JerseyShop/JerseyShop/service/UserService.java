package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.UpdateProfileRequest;
import JerseyShop.JerseyShop.dto.response.ProfileResponse;
import JerseyShop.JerseyShop.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public ProfileResponse getUserProfile(User user) throws Exception;

    public ProfileResponse updateUserProfile(User user, UpdateProfileRequest profileRequest) throws Exception;
}
