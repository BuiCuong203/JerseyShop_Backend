package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.JerseyRequest;
import JerseyShop.JerseyShop.dto.response.JerseyResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.Club;
import JerseyShop.JerseyShop.model.Jersey;
import JerseyShop.JerseyShop.model.Type;
import JerseyShop.JerseyShop.repository.ClubRepository;
import JerseyShop.JerseyShop.repository.JerseyRepository;
import JerseyShop.JerseyShop.repository.TypeRepository;
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

    @Override
    public JerseyResponse createJersey(JerseyRequest jerseyRequest) throws Exception {
        String[] tmp = jerseyRequest.getSizes().split(",");
        List<String> sizes = new ArrayList<>();
        for (String s : tmp) {
            sizes.add(s);
        }

        JerseyResponse jerseyResponse = JerseyResponse.builder()
                .name(jerseyRequest.getName())
                .description(jerseyRequest.getDescription())
                .price(jerseyRequest.getPrice())
                .discount(jerseyRequest.getDiscount())
//                .clubId(jerseyRequest.getClubId())
//                .typeId(jerseyRequest.getTypeId())
                .sizes(sizes)
                .imageUrl(jerseyRequest.getImageUrl())
                .build();

        Club club = clubRepository.findById(jerseyRequest.getClubId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_CLUB));
        Type type = typeRepository.findById(jerseyRequest.getTypeId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_TYPE));
        Jersey jersey = Jersey.builder()
                .name(jerseyRequest.getName())
                .description(jerseyRequest.getDescription())
                .price(jerseyRequest.getPrice())
                .discount(jerseyRequest.getDiscount())
                .club(club)
                .type(type)
                .images(jerseyRequest.getImageUrl())
                .build();
        jerseyRepository.save(jersey);

        return jerseyResponse;
    }

    @Override
    public JerseyResponse updateJersey(JerseyRequest jerseyRequest) throws Exception {
        return null;
    }

    @Override
    public void deleteJersey(Long jerseyId) throws Exception {

    }

    @Override
    public JerseyResponse getJerseyById(Long jerseyId) throws Exception {
        return null;
    }

    @Override
    public List<JerseyResponse> getAllJerseys() throws Exception {
        return List.of();
    }

    @Override
    public JerseyResponse searchJersey(String keyword) throws Exception {
        return null;
    }

    @Override
    public JerseyResponse addToFavourites(Long jerseyId, Long userId) throws Exception {
        return null;
    }
}
