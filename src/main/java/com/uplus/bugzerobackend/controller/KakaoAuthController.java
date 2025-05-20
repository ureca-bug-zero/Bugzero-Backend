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
    public void kakaoCallback(@RequestParam("code") String authorizationCode, HttpServletResponse response) throws Exception {
        KakaoUserDto user = kakaoAuthService.registerOrLoginWithCode(authorizationCode);
        String jwtToken = jwtTokenService.generateToken(user);

        response.setHeader("Authorization", "Bearer " + jwtToken);
//        response.sendRedirect("http://localhost:5173/kakao/callback?token=" + jwtToken);
//        response.sendRedirect("https://bugzeromej.netlify.app/kakao/callback?token=" + jwtToken);
        response.sendRedirect("https://silver-starburst-a7fd7e.netlify.app/kakao/callback?token=" + jwtToken);

    }


    // 카카오 로그아웃 처리 -> kakao-redirect-url로 redirect
    @GetMapping("/kakao/logout")
    public void kakaoLogout(HttpServletResponse response) throws Exception {
        String logoutUrl = "https://kauth.kakao.com/oauth/logout" +
                "?client_id=" + kakaoAuthService.getClientId() +
                "&logout_redirect_uri=" + kakaoAuthService.getLogoutRedirectUri();

        response.sendRedirect(logoutUrl);
    }
}


