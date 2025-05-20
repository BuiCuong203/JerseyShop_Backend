package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.TypeRequest;
import JerseyShop.JerseyShop.dto.response.TypeResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.Type;
import JerseyShop.JerseyShop.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImp implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public TypeResponse createType(TypeRequest typeRequest) {
        boolean isTypeExist = typeRepository.existsByNameType(typeRequest.getNameType());
        if(isTypeExist){
            throw new AppException(ErrorCode.TYPE_EXISTED);
        }

        TypeResponse typeResponse = TypeResponse.builder()
                .nameType(typeRequest.getNameType())
                .build();

        Type type = Type.builder()
                .nameType(typeRequest.getNameType())
                .build();
        typeRepository.save(type);

        return typeResponse;
    }

    @Override
    public TypeResponse updateType(Long id, TypeRequest typeRequest) throws Exception {
        boolean isTypeExist = typeRepository.existsByNameType(typeRequest.getNameType());
        if(isTypeExist){
            throw new AppException(ErrorCode.TYPE_EXISTED);
        }

        Type type = typeRepository.findById(id).orElse(null);
        if (type == null) {
            throw new Exception("Type not found");
        }

        type = type.toBuilder()
                .nameType(typeRequest.getNameType())
                .build();
        typeRepository.save(type);

        TypeResponse typeResponse = TypeResponse.builder()
                .id(id)
                .nameType(typeRequest.getNameType())
                .build();

        return typeResponse;
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public TypeResponse getTypeById(Long id) throws Exception {
        Type type = typeRepository.findById(id).orElse(null);
        if (type == null) {
            throw new Exception("Type not found");
        }

        TypeResponse typeResponse = TypeResponse.builder()
                .id(id)
                .nameType(type.getNameType())
                .build();

        return typeResponse;
    }

    @Override
    public List<TypeResponse> getListType() {
        List<Type> typeList = typeRepository.findAll();
        List<TypeResponse> typeResponseList = new ArrayList<>();

        for(Type type : typeList){
            TypeResponse typeResponse = TypeResponse.builder()
                    .id(type.getId())
                    .nameType(type.getNameType())
                    .build();
            typeResponseList.add(typeResponse);
        }

        return typeResponseList;
    }
}
