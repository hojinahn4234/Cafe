package com.payhere.cafe.config;

import com.payhere.cafe.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiControllerAdvice {
    // RequestBody가 입력됐을 때 @Valid에서 예외가 발생하는 경우 일괄 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {


        // 모든 에러를 불러와서 error msg에 추가
//        StringBuilder msg = new StringBuilder();
//        ex.getBindingResult().getAllErrors().forEach(c -> msg.append(c.getDefaultMessage()));

        // 첫 에러를 반환
        Response response = new Response();
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return response.fail(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchExceptions(MethodArgumentTypeMismatchException ex) {
        // 에러 메세지
        Response response = new Response();
        String msg = ex.getMessage();
        return response.fail(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleLoginExceptions(BadCredentialsException ex) {
        // 에러 메세지
        Response response = new Response();
        String msg = ex.getMessage();
        // 반환 형식에 맞춰 에러 처리
        return response.fail(msg, HttpStatus.BAD_REQUEST);
    }
}
