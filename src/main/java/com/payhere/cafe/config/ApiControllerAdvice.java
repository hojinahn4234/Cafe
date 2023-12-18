package com.spamallday.payhere.configuration;

import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.exception.CustomException;
import com.spamallday.payhere.util.JsonConverter;
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
    public ResponseEntity<JsonResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {


        // 모든 에러를 불러와서 error msg에 추가
//        StringBuilder msg = new StringBuilder();
//        ex.getBindingResult().getAllErrors().forEach(c -> msg.append(c.getDefaultMessage()));

        // 첫 에러를 반환
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 반환 형식에 맞춰 에러 처리
        JsonResponseDto json = JsonConverter.toBadJsonResponse(msg);

        return ResponseEntity.badRequest().body(json);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<JsonResponseDto> handleTypeMismatchExceptions(MethodArgumentTypeMismatchException ex) {
        // 에러 메세지
        String msg = ex.getMessage();
        // 반환 형식에 맞춰 에러 처리
        JsonResponseDto json = JsonConverter.toBadJsonResponse(msg);

        return ResponseEntity.badRequest().body(json);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<JsonResponseDto> handleLoginExceptions(BadCredentialsException ex) {
        // 에러 메세지
        String msg = ex.getMessage();
        // 반환 형식에 맞춰 에러 처리
        JsonResponseDto json = JsonConverter.toBadJsonResponse(msg);

        return ResponseEntity.badRequest().body(json);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<JsonResponseDto> handleCustomException(CustomException e) {
        return ResponseEntity.badRequest().body(JsonConverter.toBadJsonResponse(e.getMessage()));
    }
}
