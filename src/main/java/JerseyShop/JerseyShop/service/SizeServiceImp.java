package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.Size;
import JerseyShop.JerseyShop.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImp implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public Size updateQuantity(Long sizeId, Long quantity) throws Exception {
        Size size = sizeRepository.findById(sizeId).orElseThrow(() -> new AppException(ErrorCode.INVALID_SIZE));
        size.setQuantity(quantity);

        return sizeRepository.save(size);
    }
}
