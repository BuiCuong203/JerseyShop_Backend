package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.TypeRequest;
import JerseyShop.JerseyShop.dto.response.TypeResponse;

import java.util.List;

public interface TypeService {
    public TypeResponse createType(TypeRequest typeRequest);

    public TypeResponse updateType(Long id, TypeRequest typeRequest) throws Exception;

    public void deleteType(Long id);

    public TypeResponse getTypeById(Long id) throws Exception;

    public List<TypeResponse> getListType();
}
