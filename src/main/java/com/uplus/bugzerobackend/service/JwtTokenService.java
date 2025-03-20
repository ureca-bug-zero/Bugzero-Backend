package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.dto.KakaoUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private Key getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public String generateToken(KakaoUserDto user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // email을 subject로 설정
                .claim("userId", user.getId()) // userId 추가
                .claim("name", user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey)) // Key 변환 적용
                    .build()
                    .parseClaimsJws(token);
            log.debug("Token 유효함: {}", token);
            return true;
        } catch (Exception e) {
            log.error("잘못된 JWT 토큰: {}, 예외: {}", token, e.getMessage());
            return false;
        }
    }

    // 헤더에서 토큰 추출
    public String extractTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            log.debug("헤더에서 토큰 찾음");
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 부분 추출
        }
        log.debug("헤더에 토큰 없음.");
        return null;
    }

    public Integer getUserId(HttpServletRequest request) {
        String token = extractTokenFromHeader(request);

        if (token == null || !validateToken(token)) {
            throw new SecurityException("유효하지 않은 토큰입니다.");
        }

        return extractUserIdFromToken(token);
    }

    // 토큰에서 userId 추출
    public Integer extractUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey)) // Key 변환 적용
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object userIdObject = claims.get("userId");
        if (userIdObject == null) {
            throw new SecurityException("JWT에서 userId를 찾을 수 없습니다.");
        }

        Integer userId = Integer.parseInt(userIdObject.toString()); // userId 변환
        log.debug("추출된 userId: {}", userId);
        return userId;
    }
}