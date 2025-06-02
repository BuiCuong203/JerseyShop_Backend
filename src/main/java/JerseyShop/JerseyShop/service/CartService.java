package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.AddCartItemRequest;
import JerseyShop.JerseyShop.dto.response.CartItemResponse;
import JerseyShop.JerseyShop.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    public CartItemResponse addItemToCart(AddCartItemRequest addCartItemRequest, String jwt) throws Exception;

    public CartItemResponse updateCartItemQuantity(Long cartItemId, int quantity, String jwt) throws Exception;

    public CartResponse removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    public CartResponse removeListItemFromCart(List<Long> cartItemIds, String jwt) throws Exception;

    public int getCartTotalItems(Long cartId) throws Exception;

    public Long getCartTotalPrice(Long cartId) throws Exception;

    public CartResponse findCartByUserId(String jwt) throws Exception;

    public void clearCart(String jwt) throws Exception;
}
