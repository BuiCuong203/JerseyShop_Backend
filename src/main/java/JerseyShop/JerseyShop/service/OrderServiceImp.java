package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.constant.DefinedOrderStatus;
import JerseyShop.JerseyShop.dto.request.OrderRequest;
import JerseyShop.JerseyShop.dto.response.OrderItemResponse;
import JerseyShop.JerseyShop.dto.response.OrderResponse;
import JerseyShop.JerseyShop.dto.response.PaymentResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.*;
import JerseyShop.JerseyShop.repository.OrderItemRepository;
import JerseyShop.JerseyShop.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public Object createOrder(OrderRequest request, String jwt, HttpServletRequest req) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCart(jwt);
        Address address = Address.builder()
                .Province(request.getProvince())
                .District(request.getDistrict())
                .Ward(request.getWard())
                .StreetName(request.getStreetName())
                .build();

        Order order = Order.builder()
                .customer(user)
                .orderCode(getRandomNumber(8))
                .fullname(request.getFullname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .deliveryAddress(address)
                .createdAt(new Date())
                .orderStatus(DefinedOrderStatus.PENDING)
                .methodPayment(request.getMethodPayment())
                .paymentStatus(false)
                .totalItem(cart.getTotalItem())
                .totalPrice(cart.getTotalPrice())
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
        }
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(jwt);

        var object = new Object();
        if(order.getMethodPayment().equals("CASH")){
            OrderResponse orderResponse = updatePaymentStatus(savedOrder.getOrderCode());
            object = orderResponse;
        }
        else if(order.getMethodPayment().equals("VNPAY")) {
            PaymentResponse paymentResponse = paymentService.createVNPayPayment(req, order.getTotalPrice(), order.getOrderCode());
            object = paymentResponse;
        }

        return object;
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

        return getOrderById(orderId);
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

        return getOrderById(orderId);
    }

    @Override
    public List<OrderResponse> getOrderByUser(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderRepository.findByCustomerId(user.getId());

        return orders.stream().map(order -> getOrderById(order.getId())).toList();
    }

    @Override
    public List<OrderResponse> getAllOrder(String orderStatus) {
        List<Order> orders = orderRepository.findAll();
        if(orderStatus != null && !orderStatus.isEmpty()) {
            orders = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(orderStatus))
                    .toList();
        }

        return orders.stream().map(order -> getOrderById(order.getId())).toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ORDER));

        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .fullname(order.getFullname())
                .email(order.getEmail())
                .phone(order.getPhone())
                .deliveryAddress(order.getDeliveryAddress())
                .createdAt(order.getCreatedAt())
                .orderStatus(order.getOrderStatus())
                .methodPayment(order.getMethodPayment())
                .paymentStatus(order.isPaymentStatus())
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
    public OrderResponse updatePaymentStatus(String orderCode) throws Exception {
        Order order = orderRepository.findByOrderCode(orderCode);
        order.setPaymentStatus(true);
        orderRepository.save(order);

        return getOrderById(order.getId());
    }

    private String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
