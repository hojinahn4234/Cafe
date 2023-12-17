package com.payhere.cafe.dto.request;

import lombok.Data;

@Data
public class TokenRequestDTO {
    private String accessToken;
    private String refreshToken;
}