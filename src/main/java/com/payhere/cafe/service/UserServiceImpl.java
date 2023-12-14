package com.payhere.cafe.service;

import com.payhere.cafe.domain.dto.UserDTO;
import com.payhere.cafe.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper mapper;

    @Override
    public int join(UserDTO user) throws NoSuchAlgorithmException {
        UserDTO requestUser = new UserDTO();
        requestUser.setPhoneNum(user.getPhoneNum());
        requestUser.setPw(encrypt(user.getPw()));
        if(mapper.join(requestUser) == 1) {
            return 200;
        } else {
            return 403;
        }
    }

    @Override
    public int login(String phoneNum, String pw) throws NoSuchAlgorithmException {
        if(mapper.login(phoneNum, encrypt(pw)) == 1) {
            return 200;
        } else {
            return 403;
        }
    }

    private String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
