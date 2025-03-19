package com.uplus.bugzerobackend.controller;

import com.uplus.bugzerobackend.dto.KakaoUserDto;
import com.uplus.bugzerobackend.service.KakaoAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    public KakaoAuthController(KakaoAuthService kakaoAuthService) {
        this.kakaoAuthService = kakaoAuthService;
    }

    /**
     * 카카오 회원가입 및 로그인 (Access Token 사용)
     */
    @PostMapping("/kakao/signup")
    public ResponseEntity<KakaoUserDto> kakaoSignup(@RequestParam("accessToken") String accessToken) {
        KakaoUserDto user = kakaoAuthService.registerOrLogin(accessToken);
        return ResponseEntity.ok(user);
    }
}
