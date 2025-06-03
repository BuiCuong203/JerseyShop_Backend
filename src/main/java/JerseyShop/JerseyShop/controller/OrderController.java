package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.request.OrderRequest;
import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.OrderResponse;
import JerseyShop.JerseyShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ApiResponse<OrderResponse> createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        OrderResponse orderResponse = orderService.createOrder(request, jwt);

        return ApiResponse.<OrderResponse>builder()
                .message("Đặt hàng thành công")
                .result(orderResponse)
                .build();
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ApiResponse<OrderResponse> updateOrderStatusByUser(
            @PathVariable Long orderId,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        OrderResponse orderResponse = orderService.updateStatusOrderByUser(orderId, orderStatus, jwt);

        return ApiResponse.<OrderResponse>builder()
                .message("Cập nhật trạng thái đơn hàng thành công")
                .result(orderResponse)
                .build();
    }

    @GetMapping("/order/user")
    public ApiResponse<List<OrderResponse>> getOrderByUser(
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        List<OrderResponse> orderResponses = orderService.getOrderByUser(jwt);

        return ApiResponse.<List<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng của người dùng thành công")
                .result(orderResponses)
                .build();
    }

    @GetMapping("/order/{id}")
    public ApiResponse<OrderResponse> getOrderById(
            @PathVariable Long id) {
        OrderResponse orderResponse = orderService.getOrderById(id);

        return ApiResponse.<OrderResponse>builder()
                .message("Lấy thông tin đơn hàng thành công")
                .result(orderResponse)
                .build();
    }
}
