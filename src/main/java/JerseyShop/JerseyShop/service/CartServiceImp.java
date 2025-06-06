package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.AddCartItemRequest;
import JerseyShop.JerseyShop.dto.response.CartItemResponse;
import JerseyShop.JerseyShop.dto.response.CartResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.*;
import JerseyShop.JerseyShop.repository.CartItemRepository;
import JerseyShop.JerseyShop.repository.CartRepository;
import JerseyShop.JerseyShop.repository.JerseyRepository;
import JerseyShop.JerseyShop.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JerseyRepository jerseyRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public CartItemResponse addItemToCart(AddCartItemRequest addCartItemRequest, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Jersey jersey = jerseyRepository.findById(addCartItemRequest.getJerseyId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_JERSEY));
        Size size = sizeRepository.findById(addCartItemRequest.getSizeId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_SIZE));
        Cart cart = cartRepository.findByCustomerId(user.getId());

        if(size.getQuantity() < addCartItemRequest.getQuantity()) {
            throw new AppException(ErrorCode.OVER_QUANTITY);
        }

        for(CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getJersey().equals(jersey) && cartItem.getSize().equals(size)) {
                int quantity = cartItem.getQuantity() + addCartItemRequest.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), quantity, jwt);
            }
        }

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .jersey(jersey)
                .size(size)
                .quantity(addCartItemRequest.getQuantity())
                .totalPrice(addCartItemRequest.getQuantity() * jersey.getPrice())
                .build();

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        cart.getCartItems().add(savedCartItem);

        cart.setTotalItem(getCartTotalItems(cart.getId()));
        cart.setTotalPrice(getCartTotalPrice(cart.getId()));
        cartRepository.save(cart);

        CartItemResponse cartItemResponse = CartItemResponse.builder()
                .id(savedCartItem.getId())
                .nameJersey(savedCartItem.getJersey().getName())
                .nameClub(savedCartItem.getJersey().getClub().getNameClub())
                .nameType(savedCartItem.getJersey().getType().getNameType())
                .image(savedCartItem.getJersey().getImages().get(0))
                .size(savedCartItem.getSize().getSize())
                .quantity(savedCartItem.getQuantity())
                .totalPrice(savedCartItem.getTotalPrice())
                .build();

        return cartItemResponse;
    }

    @Override
    public CartItemResponse updateCartItemQuantity(Long cartItemId, int quantity, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CARTITEM));
        Size size = cartItem.getSize();
        Cart cart = cartItem.getCart();

        if(quantity == 0){
            cart.getCartItems().remove(cartItem);
            cart.setTotalItem(getCartTotalItems(cart.getId()));
            cart.setTotalPrice(getCartTotalPrice(cart.getId()));
            cartRepository.save(cart);
            return null;
        }

        if(size.getQuantity() < quantity) {
            throw new AppException(ErrorCode.OVER_QUANTITY);
        }

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(quantity * cartItem.getJersey().getPrice());
        cartItemRepository.save(cartItem);

        cart.setTotalItem(getCartTotalItems(cart.getId()));
        cart.setTotalPrice(getCartTotalPrice(cart.getId()));
        cartRepository.save(cart);

        CartItemResponse cartItemResponse = CartItemResponse.builder()
                .id(cartItem.getId())
                .nameJersey(cartItem.getJersey().getName())
                .nameClub(cartItem.getJersey().getClub().getNameClub())
                .nameType(cartItem.getJersey().getType().getNameType())
                .image(cartItem.getJersey().getImages().get(0))
                .size(cartItem.getSize().getSize())
                .quantity(quantity)
                .totalPrice(cartItem.getTotalPrice())
                .build();

        return cartItemResponse;
    }

    @Override
    public CartResponse removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CARTITEM));

        cart.getCartItems().remove(cartItem);
        cart.setTotalItem(getCartTotalItems(cart.getId()));
        cart.setTotalPrice(getCartTotalPrice(cart.getId()));
        cartRepository.save(cart);

        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
                .map(item -> CartItemResponse.builder()
                        .id(item.getId())
                        .nameJersey(item.getJersey().getName())
                        .nameClub(item.getJersey().getClub().getNameClub())
                        .nameType(item.getJersey().getType().getNameType())
                        .image(item.getJersey().getImages().get(0))
                        .size(item.getSize().getSize())
                        .quantity(item.getQuantity())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .toList();

        CartResponse cartResponse = CartResponse.builder()
                .id(cart.getId())
                .totalItems(getCartTotalItems(cart.getId()))
                .totalPrice(getCartTotalPrice(cart.getId()))
                .cartItems(cartItemResponses)
                .build();

        return cartResponse;
    }

    @Override
    public CartResponse removeListItemFromCart(List<Long> cartItemIds, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CARTITEM));
            cart.getCartItems().remove(cartItem);
        }
        cart.setTotalItem(getCartTotalItems(cart.getId()));
        cart.setTotalPrice(getCartTotalPrice(cart.getId()));
        cartRepository.save(cart);

        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
                .map(item -> CartItemResponse.builder()
                        .id(item.getId())
                        .nameJersey(item.getJersey().getName())
                        .nameClub(item.getJersey().getClub().getNameClub())
                        .nameType(item.getJersey().getType().getNameType())
                        .image(item.getJersey().getImages().get(0))
                        .size(item.getSize().getSize())
                        .quantity(item.getQuantity())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .toList();

        CartResponse cartResponse = CartResponse.builder()
                .id(cart.getId())
                .totalItems(getCartTotalItems(cart.getId()))
                .totalPrice(getCartTotalPrice(cart.getId()))
                .cartItems(cartItemResponses)
                .build();

        return cartResponse;
    }

    @Override
    public int getCartTotalItems(Long cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CART));
        int totalItems = 0;
        for(CartItem cartItem : cart.getCartItems()) {
            totalItems += cartItem.getQuantity();
        }

        return totalItems;
    }

    @Override
    public Long getCartTotalPrice(Long cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CART));
        Long totalPrice = 0L;
        for(CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getTotalPrice();
        }

        return totalPrice;
    }

    @Override
    public CartResponse findCartByUserId(String jwt) throws Exception {
        Cart cart = findCart(jwt);

        CartResponse cartResponse = CartResponse.builder()
                .id(cart.getId())
                .totalItems(getCartTotalItems(cart.getId()))
                .totalPrice(getCartTotalPrice(cart.getId()))
                .cartItems(cart.getCartItems().stream()
                        .map(item -> CartItemResponse.builder()
                                .id(item.getId())
                                .nameJersey(item.getJersey().getName())
                                .nameClub(item.getJersey().getClub().getNameClub())
                                .nameType(item.getJersey().getType().getNameType())
                                .image(item.getJersey().getImages().get(0))
                                .size(item.getSize().getSize())
                                .quantity(item.getQuantity())
                                .totalPrice(item.getTotalPrice())
                                .build())
                        .toList())
                .build();

        return cartResponse;
    }

    @Override
    public void clearCart(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        cart.getCartItems().clear();
        cart.setTotalItem(0);
        cart.setTotalPrice(0L);
        cartRepository.save(cart);
    }

    @Override
    public Cart findCart(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return cartRepository.findByCustomerId(user.getId());
    }
}
