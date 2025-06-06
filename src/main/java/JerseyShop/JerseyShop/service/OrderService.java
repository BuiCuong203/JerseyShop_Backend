package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.request.OrderRequest;
import JerseyShop.JerseyShop.dto.response.OrderResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderService {
    public Object createOrder(OrderRequest request, String jwt, HttpServletRequest req) throws Exception;

    public OrderResponse updateStatusOrderByAdmin(Long orderId, String orderStatus);

    public OrderResponse updateStatusOrderByUser(Long orderId, String orderStatus, String jwt) throws Exception;

    public List<OrderResponse> getOrderByUser(String jwt) throws Exception;

    public List<OrderResponse> getAllOrder(String orderStatus);

    public OrderResponse getOrderById(Long orderId);

    public OrderResponse updatePaymentStatus(String orderCode) throws Exception;
}
