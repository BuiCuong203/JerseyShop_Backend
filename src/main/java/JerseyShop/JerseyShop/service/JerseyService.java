package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.JerseyRequest;
import JerseyShop.JerseyShop.dto.response.JerseyResponse;

import java.util.List;

public interface JerseyService {
    public JerseyResponse createJersey(JerseyRequest jerseyRequest, List<Long> sizesQuantity) throws Exception;

    public JerseyResponse updateJersey(Long id, JerseyRequest jerseyRequest, List<Long> sizesQuantity) throws Exception;

    public void deleteJersey(Long jerseyId) throws Exception;

    public JerseyResponse getJerseyById(Long jerseyId) throws Exception;

    public List<JerseyResponse> getAllJerseys() throws Exception;

    public List<JerseyResponse> searchJersey(String keyword) throws Exception;

    public JerseyResponse addToFavourites(Long jerseyId, Long userId) throws Exception;
}
