package com.payhere.cafe.service;

import com.payhere.cafe.domain.dto.UserDTO;
import com.payhere.cafe.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper mapper;

    @Override
    public int join(UserDTO user) {
        if(mapper.join(user) == 1) {
            return 200;
        } else {
            return 403;
        }
    }

    @Override
    public int login(String phoneNum, String pw) {
        if(mapper.login(phoneNum, pw) == 1) {
            return 200;
        } else {
            return 403;
        }
    }
}
