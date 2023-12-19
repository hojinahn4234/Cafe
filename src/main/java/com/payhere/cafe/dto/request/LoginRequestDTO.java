package com.payhere.cafe.dto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class LoginRequestDTO {
    private String phonenum;
    private String pw;
}
