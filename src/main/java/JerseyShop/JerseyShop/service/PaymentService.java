package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    public PaymentResponse createVNPayPayment(HttpServletRequest request, Long amount, String orderCode) throws UnsupportedEncodingException;
}
