package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.model.Size;

public interface SizeService {
    public Size updateQuantity(Long sizeId, Long quantity) throws Exception;
}
