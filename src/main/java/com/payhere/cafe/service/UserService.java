package com.payhere.cafe.service;

import com.payhere.cafe.dto.UserDTO;
import com.payhere.cafe.dto.request.LoginRequestDTO;
import com.payhere.cafe.dto.request.TokenRequestDTO;
import com.payhere.cafe.entity.User;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    int join(UserDTO user);

    ResponseEntity<?> login(UserDTO loginRequestDTO);

    ResponseEntity<?> reissue(TokenRequestDTO tokenRequestDTO);

    int logout(TokenRequestDTO tokenRequestDTO);
}
