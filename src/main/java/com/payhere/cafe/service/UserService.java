package com.payhere.cafe.service;

import com.payhere.cafe.entity.User;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    int join(User user) throws NoSuchAlgorithmException;

    int login(String phoneNum, String pw) throws NoSuchAlgorithmException;
}
