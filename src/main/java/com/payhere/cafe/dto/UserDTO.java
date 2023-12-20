package com.payhere.cafe.dto;


import com.payhere.cafe.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

@Data
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "잘못된 휴대폰 번호입니다. 비어있거나 공백이 있습니다.")
    private String phonenum;
    @NotBlank(message = "잘못된 비밀번호입니다. 비어있거나 공백이 있습니다.")
    private String pw;

    public User toUserEntity() {
        return User.builder()
                .phonenum(getPhonenum())
                .pw(getPw())
//                .salt(getSalt())
                // Spring Security Role add
                .roles(Collections.singletonList("ROLE_CAFE"))
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuth() {
        return new UsernamePasswordAuthenticationToken(phonenum, pw);
    }
}
