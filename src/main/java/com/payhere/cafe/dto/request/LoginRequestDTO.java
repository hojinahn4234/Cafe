package com.payhere.cafe.dto.request;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class LoginRequestDTO {
    private String phoneNum;
    private String pw;
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(phoneNum, pw);
    }
}
