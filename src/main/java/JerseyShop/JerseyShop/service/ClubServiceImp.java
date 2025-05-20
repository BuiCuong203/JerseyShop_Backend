package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.ClubRequest;
import JerseyShop.JerseyShop.dto.response.ClubResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.Club;
import JerseyShop.JerseyShop.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClubServiceImp implements ClubService{

    @Autowired
    private ClubRepository clubRepository;

    @Override
    public ClubResponse createClub(ClubRequest clubRequest) {
        boolean isClubExist = clubRepository.existsByNameClub(clubRequest.getNameClub());
        if(isClubExist){
            throw new AppException(ErrorCode.CLUB_EXISTED);
        }

        ClubResponse clubResponse = ClubResponse.builder()
                .nameClub(clubRequest.getNameClub())
                .image(clubRequest.getImage())
                .build();

        Club club = Club.builder()
                .nameClub(clubRequest.getNameClub())
                .image(clubRequest.getImage())
                .build();
        clubRepository.save(club);

        return clubResponse;
    }

    @Override
    public ClubResponse updateClub(Long id, ClubRequest clubRequest) throws Exception {
        boolean isClubExist = clubRepository.existsByNameClub(clubRequest.getNameClub());
        if(isClubExist){
            throw new AppException(ErrorCode.CLUB_EXISTED);
        }

        Club club = clubRepository.findById(id).orElse(null);
        if (club == null) {
            throw new Exception("Club not found");
        }

        club = club.toBuilder()
                .nameClub(clubRequest.getNameClub())
                .image(clubRequest.getImage())
                .build();
        clubRepository.save(club);

        ClubResponse clubResponse = ClubResponse.builder()
                .id(id)
                .nameClub(clubRequest.getNameClub())
                .image(clubRequest.getImage())
                .build();

        return clubResponse;
    }

    @Override
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

    @Override
    public ClubResponse getClubById(Long id) throws Exception {
        Club club = clubRepository.findById(id).orElse(null);
        if (club == null) {
            throw new Exception("Club not found");
        }

        ClubResponse clubResponse = ClubResponse.builder()
                .id(id)
                .nameClub(club.getNameClub())
                .image(club.getImage())
                .build();

        return clubResponse;
    }

    @Override
    public List<ClubResponse> getListClub() {
        List<Club> clubList = clubRepository.findAll();
        List<ClubResponse> clubResponseList = new ArrayList<>();

        for(Club club : clubList){
            ClubResponse clubResponse = ClubResponse.builder()
                    .id(club.getId())
                    .nameClub(club.getNameClub())
                    .image(club.getImage())
                    .build();
            clubResponseList.add(clubResponse);
        }

        return clubResponseList;
    }
}
