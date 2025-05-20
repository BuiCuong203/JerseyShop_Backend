package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.JerseyRequest;
import JerseyShop.JerseyShop.dto.response.JerseyResponse;

import java.util.List;

public interface JerseyService {
    public JerseyResponse createJersey(JerseyRequest jerseyRequest) throws Exception;

    public JerseyResponse updateJersey(JerseyRequest jerseyRequest) throws Exception;

    public void deleteJersey(Long jerseyId) throws Exception;

    public JerseyResponse getJerseyById(Long jerseyId) throws Exception;

    public List<JerseyResponse> getAllJerseys() throws Exception;

    public JerseyResponse searchJersey(String keyword) throws Exception;

    public JerseyResponse addToFavourites(Long jerseyId, Long userId) throws Exception;
}
