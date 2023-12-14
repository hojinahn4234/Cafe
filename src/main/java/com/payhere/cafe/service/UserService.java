package com.payhere.cafe.service;

import com.payhere.cafe.domain.dto.UserDTO;
import com.payhere.cafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    int join(UserDTO user) throws NoSuchAlgorithmException;

    int login(String phoneNum, String pw) throws NoSuchAlgorithmException;
}
