package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.AddCartItemRequest;
import JerseyShop.JerseyShop.dto.request.UpdateCartItemRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.CartItemResponse;
import JerseyShop.JerseyShop.dto.response.CartResponse;
import JerseyShop.JerseyShop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("/cart/add")
    public ApiResponse<CartItemResponse> addItemToCart(
            @RequestBody AddCartItemRequest addCartItemRequest,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        CartItemResponse cartItemResponse = cartService.addItemToCart(addCartItemRequest, jwt);

        return ApiResponse.<CartItemResponse>builder()
                .message("Thêm vào giỏ hàng thành công")
                .result(cartItemResponse)
                .build();
    }

    @PutMapping("/cart-item/update")
    public ApiResponse<CartItemResponse> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest updateCartItemRequest,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        CartItemResponse cartItemResponse = cartService.updateCartItemQuantity(updateCartItemRequest.getCartItemId(), updateCartItemRequest.getQuantity(), jwt);

        return ApiResponse.<CartItemResponse>builder()
                .message("Cập nhật số lượng sản phẩm trong giỏ hàng thành công")
                .result(cartItemResponse)
                .build();
    }

    @DeleteMapping("/cart-item/remove/{id}")
    public ApiResponse<CartResponse> removeCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {
        CartResponse cartResponse = cartService.removeItemFromCart(id, jwt);

        return ApiResponse.<CartResponse>builder()
                .message("Xóa sản phẩm khỏi giỏ hàng thành công")
                .result(cartResponse)
                .build();
    }

    @DeleteMapping("/cart-item/remove-list")
    public ApiResponse<CartResponse> removeListItemFromCart(
            @RequestBody List<Long> cartItemIds,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        CartResponse cartResponse = cartService.removeListItemFromCart(cartItemIds, jwt);

        return ApiResponse.<CartResponse>builder()
                .message("Xóa danh sách sản phẩm khỏi giỏ hàng thành công")
                .result(cartResponse)
                .build();
    }

    @GetMapping("/cart")
    public ApiResponse<CartResponse> getCartUser(@RequestHeader("Authorization") String jwt) throws Exception {
        CartResponse cartResponse = cartService.findCartByUserId(jwt);

        return ApiResponse.<CartResponse>builder()
                .message("Lấy giỏ hàng thành công")
                .result(cartResponse)
                .build();
    }

    @DeleteMapping("/cart/clear")
    public ApiResponse<Void> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        cartService.clearCart(jwt);

        return ApiResponse.<Void>builder()
                .message("Giỏ hàng đã được xóa")
                .build();
    }
}
