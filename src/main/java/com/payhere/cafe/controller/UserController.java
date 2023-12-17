package com.payhere.cafe.controller;

import com.payhere.cafe.dto.Response;
import com.payhere.cafe.dto.request.LoginRequestDTO;
import com.payhere.cafe.dto.request.TokenRequestDTO;
import com.payhere.cafe.entity.User;
import com.payhere.cafe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    private Response response;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User user) throws NoSuchAlgorithmException {
        int code = service.join(user);
        if(code == 200) {
            return response.success(null, "ok", HttpStatus.OK);
        }
        else if(code == 2) {
            return response.fail("회원가입에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
        else {
            return response.fail( "이미 존재하는 유저입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws NoSuchAlgorithmException {
        int code = service.login(loginRequestDTO);
        if(code == 200) {
            return response.success(null, "ok", HttpStatus.OK);
        }
        else if(code == 2) {
            return response.fail("비밀번호를 틀리셨습니다.", HttpStatus.BAD_REQUEST);
        }
        else {
            return response.fail("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(TokenRequestDTO tokenRequestDTO) {
        int code = service.logout(tokenRequestDTO);
        if(code == 200) {
            return response.success(null, "ok", HttpStatus.OK);
        }
        else {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
