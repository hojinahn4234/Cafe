package com.payhere.cafe.controller;

import com.payhere.cafe.dto.UserDTO;
import com.payhere.cafe.dto.response.Response;
import com.payhere.cafe.dto.request.LoginRequestDTO;
import com.payhere.cafe.dto.request.TokenRequestDTO;
import com.payhere.cafe.entity.User;
import com.payhere.cafe.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl service;
    private final Response response;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserDTO user) {
        try {
            int code = service.join(user);
            if(code == 200) {
                return response.success("", "ok", HttpStatus.OK);
            }
            else if(code == 3) {
                return response.fail("비밀번호 암호화에 실패하였습니다. 죄송합니다.", HttpStatus.BAD_REQUEST);
            }
            else if(code == 2) {
                return response.fail("회원가입에 실패하였습니다.", HttpStatus.BAD_REQUEST);
            }
            else {
                return response.fail( "이미 존재하는 유저입니다.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO loginRequestDTO) {
        try {
            return service.login(loginRequestDTO);
        } catch (Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenRequestDTO tokenRequestDTO) {
        try {
            return service.reissue(tokenRequestDTO);
        } catch (Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(TokenRequestDTO tokenRequestDTO) {
        try {
            int code = service.logout(tokenRequestDTO);
            if(code == 200) {
                return response.success("", "ok", HttpStatus.OK);
            }
            else {
                return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
