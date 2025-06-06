package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.config.payment.VNPayConfig;
import JerseyShop.JerseyShop.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class PaymentServiceImp implements PaymentService {

    @Autowired
    private VNPayConfig vnPayConfig;

    @Override
    public PaymentResponse createVNPayPayment(HttpServletRequest request, Long amount, String orderCode) throws UnsupportedEncodingException {
        amount = amount * 100L;
        String IpAddr = vnPayConfig.getIpAddress(request);
        Map<String, String> vnp_Params = vnPayConfig.getVNPayConfig();
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_IpAddr", IpAddr);
        vnp_Params.put("vnp_TxnRef", orderCode);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + orderCode);

        String queryUrl = vnPayConfig.hashAllFields(vnp_Params, true);
        String hashData = vnPayConfig.hashAllFields(vnp_Params, false);

        queryUrl += "&vnp_SecureHash=" + hashData;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return PaymentResponse.builder()
                .paymentUrl(paymentUrl)
                .build();
    }
}
