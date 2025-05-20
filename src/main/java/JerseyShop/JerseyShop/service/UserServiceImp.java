package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.config.JwtProvider;
import JerseyShop.JerseyShop.dto.request.updateProfileRequest;
import JerseyShop.JerseyShop.dto.response.ProfileResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.Address;
import JerseyShop.JerseyShop.model.User;
import JerseyShop.JerseyShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new Exception("User not found");
        }

        return user;
    }

    @Override
    public ProfileResponse getUserProfile(User user) throws Exception {
        ProfileResponse profileResponse = ProfileResponse.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .address(user.getAddress())
                .build();

        return profileResponse;
    }

    @Override
    public ProfileResponse updateUserProfile(User user, updateProfileRequest profileRequest) throws Exception {
        Address address = Address.builder()
                .Province(profileRequest.getProvince())
                .District(profileRequest.getDistrict())
                .Ward(profileRequest.getWard())
                .StreetName(profileRequest.getStreetName())
                .build();

        ProfileResponse profileResponse = ProfileResponse.builder()
                .id(user.getId())
                .fullname(profileRequest.getFullname())
                .email(user.getEmail())
                .phone(profileRequest.getPhone())
                .address(address)
                .role(user.getRole())
                .build();

        user = user.toBuilder()
                .address(address)
                .fullname(profileRequest.getFullname())
                .phone(profileRequest.getPhone())
                .build();
        userRepository.save(user);

        return profileResponse;
    }
}
