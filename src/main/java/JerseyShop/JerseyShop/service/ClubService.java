package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.ClubRequest;
import JerseyShop.JerseyShop.dto.response.ClubResponse;

import java.util.List;

public interface ClubService {
    public ClubResponse createClub(ClubRequest clubRequest);

    public ClubResponse updateClub(Long id, ClubRequest clubRequest) throws Exception;

    public void deleteClub(Long id);

    public ClubResponse getClubById(Long id) throws Exception;

    public List<ClubResponse> getListClub();
}
