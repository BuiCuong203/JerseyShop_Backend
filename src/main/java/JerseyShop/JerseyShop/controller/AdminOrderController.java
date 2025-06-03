package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.OrderResponse;
import JerseyShop.JerseyShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ApiResponse<OrderResponse> updateOrderStatusByAdmin(
            @PathVariable Long orderId,
            @PathVariable String orderStatus)
            throws Exception {
        OrderResponse orderResponse = orderService.updateStatusOrderByAdmin(orderId, orderStatus);

        return ApiResponse.<OrderResponse>builder()
                .message("Cập nhật trạng thái đơn hàng thành công")
                .result(orderResponse)
                .build();
    }

    @GetMapping("/order")
    public ApiResponse<List<OrderResponse>> getAllOrder(
            @RequestParam(required = false) String orderStatus) {
        List<OrderResponse> orderResponses = orderService.getAllOrder(orderStatus);

        return ApiResponse.<List<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng thành công")
                .result(orderResponses)
                .build();
    }
}
