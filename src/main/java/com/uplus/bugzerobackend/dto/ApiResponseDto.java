package com.uplus.bugzerobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {
    //응답 공통 구조 정의
    private boolean success;
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponseDto<T> success(String message, T data){
        return new ApiResponseDto<>(true, "S200", message, data);
    }

    public static <T> ApiResponseDto<T> failure(String code, String message){
        return new ApiResponseDto<>(false, code, message,null);
    }
}