package com.uplus.bugzerobackend.controller;

import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.dto.KakaoUserDto;
import com.uplus.bugzerobackend.service.KakaoAuthService;
import com.uplus.bugzerobackend.service.JwtTokenService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenService jwtTokenService;

    public KakaoAuthController(KakaoAuthService kakaoAuthService, JwtTokenService jwtTokenService) {
        this.kakaoAuthService = kakaoAuthService;
        this.jwtTokenService = jwtTokenService;
    }

    // 카카오 로그인 URL로 리다이렉트
    @GetMapping("/kakao/login")
    public void kakaoLogin(HttpServletResponse response) throws Exception {
        String redirectUrl = kakaoAuthService.getKakaoLoginUrl();
        response.sendRedirect(redirectUrl);
    }

    // 카카오 콜백 처리
    @GetMapping("/kakao/callback")
    public ResponseEntity<ApiResponseDto<String>> kakaoCallback(@RequestParam("code") String authorizationCode) {
        KakaoUserDto user = kakaoAuthService.registerOrLoginWithCode(authorizationCode);
        
        // JWT 토큰 생성
        String jwtToken = jwtTokenService.generateToken(user);
        return ResponseEntity.ok(ApiResponseDto.success("로그인 성공", jwtToken));
    }
}
