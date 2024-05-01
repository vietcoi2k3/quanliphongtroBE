package com.apec.pos.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentDTO {
    public String code;
    public String message;
    public String paymentUrl;
}
