package com.payhere.cafe.controller;

import com.payhere.cafe.domain.dto.UserDTO;
import com.payhere.cafe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/join")
    public ResponseEntity<?> join(UserDTO user) {
        if(service.join(user) == 200) {
            return ResponseEntity.ok(null);
        }
        else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(String phoneNum, String pw) {
        if(service.login(phoneNum, pw) == 200) {
            return ResponseEntity.ok(null);
        }
        else {
            return ResponseEntity.badRequest().body(null);
            return null;
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return null;
    }
}
