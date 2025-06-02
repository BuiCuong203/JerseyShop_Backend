package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.JerseyRequest;
import JerseyShop.JerseyShop.dto.response.JerseyResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.*;
import JerseyShop.JerseyShop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JerseyServiceImp implements JerseyService{

    @Autowired
    private JerseyRepository jerseyRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeService sizeService;

    @Override
    public JerseyResponse createJersey(JerseyRequest jerseyRequest, List<Integer> sizesQuantity) throws Exception {
        boolean existsJersey = jerseyRepository.existsByName(jerseyRequest.getName());
        if(existsJersey){
            throw new AppException(ErrorCode.JERSEY_EXISTED);
        }

        Club club = clubRepository.findById(jerseyRequest.getClubId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CLUB));
        Type type = typeRepository.findById(jerseyRequest.getTypeId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_TYPE));

        String[] tmp = jerseyRequest.getSizes().trim().split(",");
        List<Size> sizes = new ArrayList<>();
        for (String s : tmp) {
            sizes.add(Size.builder().size(s.trim()).build());
        }

        Jersey jersey = Jersey.builder()
                .name(jerseyRequest.getName())
                .description(jerseyRequest.getDescription())
                .price(jerseyRequest.getPrice())
                .discount(jerseyRequest.getDiscount())
                .club(club)
                .type(type)
                .rating(0.0)
                .sizes(sizes)
                .images(jerseyRequest.getImageUrl())
                .build();
        for (Size size : sizes) {
            size.setJersey(jersey);
        }
        Jersey savedJersey = jerseyRepository.save(jersey);

        for (int i = 0; i < sizes.size(); i++) {
            Size size = sizes.get(i);
            sizeService.updateQuantity(size.getId(), sizesQuantity.get(i));
        }

        JerseyResponse jerseyResponse = JerseyResponse.builder()
                .id(savedJersey.getId())
                .name(jerseyRequest.getName())
                .description(jerseyRequest.getDescription())
                .price(jerseyRequest.getPrice())
                .discount(jerseyRequest.getDiscount())
                .nameClub(club.getNameClub())
                .nameType(type.getNameType())
                .rating(0.0)
                .sizes(sizes)
                .imageUrl(jerseyRequest.getImageUrl())
                .build();

        return jerseyResponse;
    }

    @Override
    public JerseyResponse updateJersey(Long id, JerseyRequest jerseyRequest, List<Integer> sizesQuantity) throws Exception {
        boolean existsJersey = jerseyRepository.existsByName(jerseyRequest.getName());
        if(existsJersey){
            throw new AppException(ErrorCode.JERSEY_EXISTED);
        }

        Club club = clubRepository.findById(jerseyRequest.getClubId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CLUB));
        Type type = typeRepository.findById(jerseyRequest.getTypeId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_TYPE));

        String[] tmp = jerseyRequest.getSizes().trim().split(",");
        List<Size> sizes = new ArrayList<>();
        for (String s : tmp) {
            sizes.add(Size.builder().size(s.trim()).build());
        }

        Jersey jersey = jerseyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_JERSEY));
        jersey = jersey.toBuilder()
                .name(jerseyRequest.getName())
                .description(jerseyRequest.getDescription())
                .price(jerseyRequest.getPrice())
                .discount(jerseyRequest.getDiscount())
                .club(club)
                .type(type)
                .sizes(sizes)
                .images(jerseyRequest.getImageUrl())
                .build();
        for (Size size : sizes) {
            size.setJersey(jersey);
            sizeRepository.save(size);
        }
        jerseyRepository.save(jersey);

        for (int i = 0; i < sizes.size(); i++) {
            Size size = sizes.get(i);
            sizeService.updateQuantity(size.getId(), sizesQuantity.get(i));
        }

        JerseyResponse jerseyResponse = JerseyResponse.builder()
                .id(jersey.getId())
                .name(jerseyRequest.getName())
                .description(jerseyRequest.getDescription())
                .price(jerseyRequest.getPrice())
                .discount(jerseyRequest.getDiscount())
                .nameClub(club.getNameClub())
                .nameType(type.getNameType())
                .rating(jersey.getRating())
                .sizes(sizes)
                .imageUrl(jerseyRequest.getImageUrl())
                .build();

        return jerseyResponse;
    }

    @Override
    public void deleteJersey(Long jerseyId) throws Exception {
        jerseyRepository.deleteById(jerseyId);
    }

    @Override
    public JerseyResponse getJerseyById(Long jerseyId) throws Exception {
        Jersey jersey = jerseyRepository.findById(jerseyId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_JERSEY));

        JerseyResponse jerseyResponse = JerseyResponse.builder()
                .id(jersey.getId())
                .name(jersey.getName())
                .description(jersey.getDescription())
                .price(jersey.getPrice())
                .discount(jersey.getDiscount())
                .nameClub(jersey.getClub().getNameClub())
                .nameType(jersey.getType().getNameType())
                .rating(jersey.getRating())
                .sizes(jersey.getSizes())
                .imageUrl(jersey.getImages())
                .build();
        return jerseyResponse;
    }

    @Override
    public List<JerseyResponse> getAllJerseys() throws Exception {
        List<Jersey> jerseys = jerseyRepository.findAll();
        List<JerseyResponse> jerseyResponses = new ArrayList<>();
        for (Jersey jersey : jerseys) {
            JerseyResponse jerseyResponse = JerseyResponse.builder()
                    .id(jersey.getId())
                    .name(jersey.getName())
                    .description(jersey.getDescription())
                    .price(jersey.getPrice())
                    .discount(jersey.getDiscount())
                    .nameClub(jersey.getClub().getNameClub())
                    .nameType(jersey.getType().getNameType())
                    .rating(jersey.getRating())
                    .sizes(jersey.getSizes())
                    .imageUrl(jersey.getImages())
                    .build();
            jerseyResponses.add(jerseyResponse);
        }

        return jerseyResponses;
    }

    @Override
    public List<JerseyResponse> searchJersey(String keyword) throws Exception {
        List<Jersey> jerseys = jerseyRepository.searchJersey(keyword);
        List<JerseyResponse> jerseyResponses = new ArrayList<>();
        for (Jersey jersey : jerseys) {
            JerseyResponse jerseyResponse = JerseyResponse.builder()
                    .id(jersey.getId())
                    .name(jersey.getName())
                    .description(jersey.getDescription())
                    .price(jersey.getPrice())
                    .discount(jersey.getDiscount())
                    .nameClub(jersey.getClub().getNameClub())
                    .nameType(jersey.getType().getNameType())
                    .rating(jersey.getRating())
                    .sizes(jersey.getSizes())
                    .imageUrl(jersey.getImages())
                    .build();
            jerseyResponses.add(jerseyResponse);
        }

        return jerseyResponses;
    }

    @Override
    public JerseyResponse addToFavourites(Long jerseyId, Long userId) throws Exception {
        Jersey jersey = jerseyRepository.findById(jerseyId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_JERSEY));
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));

        JerseyResponse jerseyResponse = JerseyResponse.builder()
                .id(jersey.getId())
                .name(jersey.getName())
                .description(jersey.getDescription())
                .price(jersey.getPrice())
                .discount(jersey.getDiscount())
                .nameClub(jersey.getClub().getNameClub())
                .nameType(jersey.getType().getNameType())
                .rating(jersey.getRating())
                .sizes(jersey.getSizes())
                .imageUrl(jersey.getImages())
                .build();

        boolean isFavourites = false;
        for (Jersey jersey1 : user.getWishlist()) {
            if (jersey1.getId().equals(jerseyId)) {
                isFavourites = true;
                break;
            }
        }
        if (!isFavourites) {
            user.getWishlist().add(jersey);
        } else {
            user.getWishlist().removeIf(favourite -> favourite.getId().equals(jerseyId));
        }
        userRepository.save(user);

        return jerseyResponse;
    }
}