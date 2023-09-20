package com.engineerLee.rolloverapi.payment.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private String userName;
    private String pin;
    private String amount;
    private String bankName;
}
