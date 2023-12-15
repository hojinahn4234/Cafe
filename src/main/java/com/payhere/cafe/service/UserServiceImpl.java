package com.payhere.cafe.service;

import com.payhere.cafe.entity.User;
import com.payhere.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;

    @Override
    public int join(User user) throws NoSuchAlgorithmException {
        User requestUser = new User();
        requestUser.setPhoneNum(user.getPhoneNum());
        requestUser.setPw(encrypt(user.getPw()));
        if(repository.findByPhoneNum(user.getPhoneNum()) == null) {
            if(repository.save(requestUser) != null) {
                return 200;
            } else {
                return 2;
            }
        }
        else {
            return 1;
        }
    }

    @Override
    public int login(String phoneNum, String pw) throws NoSuchAlgorithmException {
        User user = repository.findByPhoneNum(phoneNum);
        if(user != null) {
            if((user.getPw()).equals(encrypt(pw))) {
                return 200;
            }
            else {
                return 2;
            }
        } else {
            return 1;
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
