package com.payhere.cafe.controller;

import com.payhere.cafe.entity.User;
import com.payhere.cafe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(User user) throws NoSuchAlgorithmException {
        if(service.join(user) == 200) {
            return ResponseEntity
                    .ok(null);
        }
        else {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(String phoneNum, String pw) throws NoSuchAlgorithmException {
        if(service.login(phoneNum, pw) == 200) {
            return ResponseEntity
                    .ok(null);
        }
        else {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity
                .ok(null);
    }
}
