package com.uplus.bugzerobackend.controller;


import com.uplus.bugzerobackend.dto.ApiResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    //IllegalArgumentException오류 발생했을때 (userid없거나 newsid없거나 인덱스 범위 벗어나거나 등등등...)
    //그냥 예외 던지면 500internal error라고만 떠서 무슨문제인지 모름..
    //이걸로 에러 코드 +오류메시지 표시하는것


    // 400 Bad Request   - 필수 매개변수 누락.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
    	ex.printStackTrace();
        return ResponseEntity.badRequest()
                .body(ApiResponseDto.failure("E400", ex.getMessage()));
    }

    // 401 - Unauthorized  - 토큰 오류
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleUnauthorizedException(SecurityException ex) {
    	ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseDto.failure("E401", ex.getMessage()));
    }

    // 404 - Not Found  - 요청한 데이터에 해당하는 리소스 없음
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
    	ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.failure("E404", ex.getMessage()));
    }

    // 500 Internal Server Error - 논리적 오류 처리(데이터 불일치)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleIllegalStateException(IllegalStateException ex) {
    	ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.failure("E500", ex.getMessage()));
    }

    // 500 - 기타 서버 내부 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Object>> handleGeneralException(Exception ex) {
    	ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.failure("E500", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요."));
    }

}
