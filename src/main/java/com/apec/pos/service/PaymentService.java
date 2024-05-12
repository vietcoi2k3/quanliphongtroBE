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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

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
        String bankCode = "NCB";
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", "NCB");
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnpParamsMap.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
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
    public HistoryPaymentEntity savePayment(HttpServletRequest httpServletRequest) {
        // Tạo một đối tượng HistoryPaymentEntity mới để lưu thông tin thanh toán
        HistoryPaymentEntity historyPaymentEntity = new HistoryPaymentEntity();

        // Tìm tài khoản của người dùng dựa trên thông tin được trích xuất từ JWT trong request
        AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromRequest(httpServletRequest));

        // Cập nhật số tiền trong tài khoản của người dùng sau khi thanh toán
        accountEntity.setMoney(accountEntity.getMoney() + Long.parseLong(httpServletRequest.getParameter("vnp_Amount"))/100);

        // Đặt ID của tài khoản cho đối tượng lịch sử thanh toán
        historyPaymentEntity.setAccountEntityId(accountEntity.getId());

        // Đặt mô tả của thanh toán từ thông tin trích xuất từ request
        historyPaymentEntity.setDescriptions(httpServletRequest.getParameter("vnp_OrderInfo"));

        // Đặt tổng số tiền thanh toán từ thông tin trích xuất từ request
        historyPaymentEntity.setTotalAmount(Long.parseLong(httpServletRequest.getParameter("vnp_Amount")));

        // Lưu thông tin thanh toán vào cơ sở dữ liệu và trả về đối tượng đã lưu
        return historyPaymentRepository.insert(historyPaymentEntity);
    }

}
