package JerseyShop.JerseyShop.controller;

import JerseyShop.JerseyShop.dto.response.ApiResponse;
import JerseyShop.JerseyShop.dto.response.OrderResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentReturnController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/vnpay-callback")
    public ApiResponse<OrderResponse> payCallbackHandler(HttpServletRequest request) throws Exception {
        String status = request.getParameter("vnp_ResponseCode");
        String orderCode = request.getParameter("vnp_TxnRef");
        OrderResponse orderResponse = orderService.updatePaymentStatus(orderCode);

        if (!status.equals("00")) {
            throw new AppException(ErrorCode.FAILED_PAYMENT);
        }

        return ApiResponse.<OrderResponse>builder()
                .message("Thanh toán thành công")
                .result(orderResponse)
                .build();
    }
}
