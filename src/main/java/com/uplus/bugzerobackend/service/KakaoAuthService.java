package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.dto.KakaoUserDto;
import com.uplus.bugzerobackend.mapper.FriendMapper;
import com.uplus.bugzerobackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Service
public class KakaoAuthService {

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final UserMapper userMapper;
    private final FriendMapper friendMapper;

    @Autowired
    public KakaoAuthService(UserMapper userMapper, FriendMapper friendMapper) {
        this.userMapper = userMapper;
        this.friendMapper = friendMapper;
    }

    /**
     * 카카오 인가 코드로 Access Token 발급
     */
    public String getAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", authorizationCode);
        params.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(KAKAO_TOKEN_URL, entity, Map.class);

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("access_token")) {
            return (String) body.get("access_token");
        }

        throw new RuntimeException("Access Token 발급 실패");
    }

    /**
     * 카카오 Access Token으로 사용자 정보 조회
     */
    public KakaoUserDto getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(KAKAO_USER_INFO_URL, entity, Map.class);

        Map<String, Object> body = response.getBody();
        if (body != null) {
            KakaoUserDto kakaoUser = new KakaoUserDto();
            kakaoUser.setName(((Map<String, String>) body.get("properties")).get("nickname"));
            kakaoUser.setEmail(((Map<String, Object>) body.get("kakao_account")).get("email").toString());
            return kakaoUser;
        }
        return null;
    }

    /**
     * 카카오 회원가입 또는 로그인 처리 (인가 코드 사용)
     */
    public KakaoUserDto registerOrLoginWithCode(String authorizationCode) {
        // 1. Access Token 발급
        String accessToken = getAccessToken(authorizationCode);
        System.out.println("Access Token 받음: " + accessToken);

        // 2. 사용자 정보 가져오기
        KakaoUserDto kakaoUser = getUserInfo(accessToken);
        if (kakaoUser == null) {
            throw new RuntimeException("카카오 사용자 정보를 가져올 수 없습니다.");
        }

        // 3. 이메일로 기존 회원 조회
        KakaoUserDto existingUser = userMapper.getUserByEmail(kakaoUser.getEmail());
        if (existingUser != null) {
            System.out.println("기존 회원 로그인: " + existingUser.getEmail());
            return existingUser; // 기존 회원이면 그대로 반환
        }

        // 4. 신규 회원 가입
        userMapper.insertUser(kakaoUser);
        System.out.println("신규 회원 가입 완료: " + kakaoUser.getEmail());

        // 5. 친구 리스트 생성
        KakaoUserDto newUser = userMapper.getUserByEmail(kakaoUser.getEmail());
        friendMapper.insertFriendList(newUser.getId());
        System.out.println("신규 회원의 친구 리스트 생성 완료");

        return newUser;
    }
}
