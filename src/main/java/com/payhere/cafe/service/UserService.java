package com.payhere.cafe.service;

import com.payhere.cafe.dto.request.LoginRequestDTO;
import com.payhere.cafe.dto.request.TokenRequestDTO;
import com.payhere.cafe.entity.User;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    int join(User user) throws NoSuchAlgorithmException;

    int login(LoginRequestDTO loginRequestDTO) throws NoSuchAlgorithmException;

    int logout(TokenRequestDTO tokenRequestDTO);
}
