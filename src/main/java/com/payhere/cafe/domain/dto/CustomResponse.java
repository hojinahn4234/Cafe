package com.payhere.cafe.domain.dto;

public class CustomResponse {
    private Meta meta;
    private Object data;

    public CustomResponse(int code, String message, Object data) {
        this.meta = new Meta(code, message);
        this.data = data;
    }

    // Getters, setters, constructors
    // 필요에 따라 추가적인 메소드 및 로직 추가 가능
}

class Meta {
    private int code;
    private String message;

    public Meta(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters, setters, constructors
    // 필요에 따라 추가적인 메소드 및 로직 추가 가능
}