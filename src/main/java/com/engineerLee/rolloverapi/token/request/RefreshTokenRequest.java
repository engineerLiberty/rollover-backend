package com.engineerLee.rolloverapi.token.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String tokenId;
}