package com.apec.pos.service;

import com.apec.pos.dto.PaymentDTO;
import com.apec.pos.entity.AccountEntity;
import com.apec.pos.entity.HistoryPaymentEntity;
import com.apec.pos.repository.AccountRepository;
import com.apec.pos.repository.HistoryPaymentRepository;
import com.apec.pos.security.VNPAYConfig;
import com.apec.pos.until.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    private final VNPAYConfig vnPayConfig;

    @Autowired
    private HistoryPaymentRepository historyPaymentRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountRepository accountRepository;

    public PaymentService(VNPAYConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }


    //tạo giao dịch vnPay
    public PaymentDTO createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", "localhost");
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    //lưu thông tin hóa đơn bao gồm giá,và orderInfo
    public HistoryPaymentEntity savePayment(HttpServletRequest httpServletRequest){
        HistoryPaymentEntity historyPaymentEntity = new HistoryPaymentEntity();
        AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));
        historyPaymentEntity.setAccountEntityId(accountEntity.getId());
        historyPaymentEntity.setDescriptions(httpServletRequest.getParameter("vnp_OrderInfo"));
        historyPaymentEntity.setTotalAmount(Long.parseLong(httpServletRequest.getParameter("vnp_Amount")));
        return  historyPaymentRepository.insert(historyPaymentEntity);
    }
}
