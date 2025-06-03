package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.constant.DefinedOrderStatus;
import JerseyShop.JerseyShop.dto.request.OrderRequest;
import JerseyShop.JerseyShop.dto.response.OrderItemResponse;
import JerseyShop.JerseyShop.dto.response.OrderResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.*;
import JerseyShop.JerseyShop.repository.CartRepository;
import JerseyShop.JerseyShop.repository.OrderItemRepository;
import JerseyShop.JerseyShop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private SizeService sizeService;

    @Override
    public OrderResponse createOrder(OrderRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Address address = Address.builder()
                .Province(request.getProvince())
                .District(request.getDistrict())
                .Ward(request.getWard())
                .StreetName(request.getStreetName())
                .build();

        Order order = Order.builder()
                .customer(user)
                .fullname(request.getFullname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .deliveryAddress(address)
                .createdAt(new Date())
                .orderStatus(DefinedOrderStatus.PENDING)
                .methodPayment(request.getMethodPayment())
                .totalItem(cart.getTotalItem())
                .totalPrice(cart.getTotalPrice())
                .items(new ArrayList<>())
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .fullname(order.getFullname())
                .email(order.getEmail())
                .phone(order.getPhone())
                .deliveryAddress(order.getDeliveryAddress())
                .createdAt(order.getCreatedAt())
                .orderStatus(order.getOrderStatus())
                .methodPayment(order.getMethodPayment())
                .totalItem(order.getTotalItem())
                .totalPrice(order.getTotalPrice())
                .items(new ArrayList<>())
                .build();

        for(CartItem item : cart.getCartItems()){
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .jersey(item.getJersey())
                    .size(item.getSize())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .build();
            order.getItems().add(orderItem);

            Size size = sizeService.findSizeById(item.getSize().getId());
            int newQuantity = size.getQuantity() - item.getQuantity();
            sizeService.updateQuantity(size.getId(), newQuantity);

            OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                    .id(orderItem.getId())
                    .nameJersey(item.getJersey().getName())
                    .size(item.getSize().getSize())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .build();
            orderResponse.getItems().add(orderItemResponse);
        }
        Order savedOrder = orderRepository.save(order);
        orderResponse.setId(savedOrder.getId());
        cartService.clearCart(jwt);

        return orderResponse;
    }

    @Override
    public OrderResponse updateStatusOrderByAdmin(Long orderId, String orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ORDER));

        if (orderStatus.equals(DefinedOrderStatus.PENDING)
                || orderStatus.equals(DefinedOrderStatus.CONFIRMED)
                || orderStatus.equals(DefinedOrderStatus.DELIVERED)
                || orderStatus.equals(DefinedOrderStatus.COMPLETED)
                || orderStatus.equals(DefinedOrderStatus.CANCELED)) {
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        }else{
            throw new AppException(ErrorCode.WRONG_ORDER_STATUS);
        }

        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .fullname(order.getFullname())
                .email(order.getEmail())
                .phone(order.getPhone())
                .deliveryAddress(order.getDeliveryAddress())
                .createdAt(order.getCreatedAt())
                .orderStatus(order.getOrderStatus())
                .methodPayment(order.getMethodPayment())
                .totalItem(order.getTotalItem())
                .totalPrice(order.getTotalPrice())
                .items(new ArrayList<>())
                .build();

        for (OrderItem item : order.getItems()) {
            OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                    .id(item.getId())
                    .nameJersey(item.getJersey().getName())
                    .size(item.getSize().getSize())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .build();
            orderResponse.getItems().add(orderItemResponse);
        }

        return orderResponse;
    }

    @Override
    public OrderResponse updateStatusOrderByUser(Long orderId, String orderStatus, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ORDER));

        if(!user.getId().equals(order.getCustomer().getId())){
            throw new AppException(ErrorCode.NOT_FOUND_ORDER);
        }

        if (order.getOrderStatus().equals(DefinedOrderStatus.PENDING) && orderStatus.equals(DefinedOrderStatus.CANCELED)) {
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        }else{
            throw new AppException(ErrorCode.WRONG_ORDER_STATUS);
        }

        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .fullname(order.getFullname())
                .email(order.getEmail())
                .phone(order.getPhone())
                .deliveryAddress(order.getDeliveryAddress())
                .createdAt(order.getCreatedAt())
                .orderStatus(order.getOrderStatus())
                .methodPayment(order.getMethodPayment())
                .totalItem(order.getTotalItem())
                .totalPrice(order.getTotalPrice())
                .items(new ArrayList<>())
                .build();

        for (OrderItem item : order.getItems()) {
            OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                    .id(item.getId())
                    .nameJersey(item.getJersey().getName())
                    .size(item.getSize().getSize())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .build();
            orderResponse.getItems().add(orderItemResponse);
        }

        return orderResponse;
    }

    @Override
    public List<OrderResponse> getOrderByUser(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderRepository.findByCustomerId(user.getId());

        return orders.stream().map(order -> {
            OrderResponse orderResponse = OrderResponse.builder()
                    .id(order.getId())
                    .fullname(order.getFullname())
                    .email(order.getEmail())
                    .phone(order.getPhone())
                    .deliveryAddress(order.getDeliveryAddress())
                    .createdAt(order.getCreatedAt())
                    .orderStatus(order.getOrderStatus())
                    .methodPayment(order.getMethodPayment())
                    .totalItem(order.getTotalItem())
                    .totalPrice(order.getTotalPrice())
                    .items(new ArrayList<>())
                    .build();

            for (OrderItem item : order.getItems()) {
                OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                        .id(item.getId())
                        .nameJersey(item.getJersey().getName())
                        .size(item.getSize().getSize())
                        .quantity(item.getQuantity())
                        .totalPrice(item.getTotalPrice())
                        .build();
                orderResponse.getItems().add(orderItemResponse);
            }
            return orderResponse;
        }).toList();
    }

    @Override
    public List<OrderResponse> getAllOrder(String orderStatus) {
        List<Order> orders = orderRepository.findAll();
        if(orderStatus != null && !orderStatus.isEmpty()) {
            orders = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(orderStatus))
                    .toList();
        }

        return orders.stream().map(order -> {
            OrderResponse orderResponse = OrderResponse.builder()
                    .id(order.getId())
                    .fullname(order.getFullname())
                    .email(order.getEmail())
                    .phone(order.getPhone())
                    .deliveryAddress(order.getDeliveryAddress())
                    .createdAt(order.getCreatedAt())
                    .orderStatus(order.getOrderStatus())
                    .methodPayment(order.getMethodPayment())
                    .totalItem(order.getTotalItem())
                    .totalPrice(order.getTotalPrice())
                    .items(new ArrayList<>())
                    .build();

            for (OrderItem item : order.getItems()) {
                OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                        .id(item.getId())
                        .nameJersey(item.getJersey().getName())
                        .size(item.getSize().getSize())
                        .quantity(item.getQuantity())
                        .totalPrice(item.getTotalPrice())
                        .build();
                orderResponse.getItems().add(orderItemResponse);
            }
            return orderResponse;
        }).toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ORDER));

        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .fullname(order.getFullname())
                .email(order.getEmail())
                .phone(order.getPhone())
                .deliveryAddress(order.getDeliveryAddress())
                .createdAt(order.getCreatedAt())
                .orderStatus(order.getOrderStatus())
                .methodPayment(order.getMethodPayment())
                .totalItem(order.getTotalItem())
                .totalPrice(order.getTotalPrice())
                .items(new ArrayList<>())
                .build();

        for (OrderItem item : order.getItems()) {
            OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                    .id(item.getId())
                    .nameJersey(item.getJersey().getName())
                    .size(item.getSize().getSize())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .build();
            orderResponse.getItems().add(orderItemResponse);
        }

        return orderResponse;
    }
}
