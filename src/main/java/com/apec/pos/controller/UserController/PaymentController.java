package com.apec.pos.controller.UserController;

import com.apec.pos.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/payment")
@RequiredArgsConstructor
@CrossOrigin
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
// Phương thức này xử lý yêu cầu thanh toán từ VNPay và trả về một phản hồi HTTP.
    public ResponseEntity pay(HttpServletRequest request) {
        return ResponseEntity.ok(paymentService.createVnPayPayment(request));
    }

    @GetMapping(value = "/vn-return")
// Phương thức này xử lý việc lưu thông tin thanh toán từ VNPay và trả về một phản hồi HTTP.
    public ResponseEntity saveInfo(HttpServletRequest request){
        return ResponseEntity.ok(paymentService.savePayment(request));
    }

}
