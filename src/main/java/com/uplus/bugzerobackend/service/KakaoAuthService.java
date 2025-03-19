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
import java.util.Map;

@Service
public class KakaoAuthService {

    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    
    private final UserMapper userMapper;
    private final FriendMapper friendMapper;
    
    @Autowired
    public KakaoAuthService(UserMapper userMapper, FriendMapper friendMapper) {
        this.userMapper = userMapper;
        this.friendMapper = friendMapper;
    }

    /**
     * 카카오 Access Token을 사용하여 사용자 정보 조회
     */
    public KakaoUserDto getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

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
     * 카카오 회원가입 또는 로그인 처리
     */
    public KakaoUserDto registerOrLogin(String accessToken) {
        // 카카오에서 사용자 정보 가져오기
    	System.out.println("Access Token 받음: " + accessToken);
        KakaoUserDto kakaoUser = getUserInfo(accessToken);

        if (kakaoUser == null) {
            throw new RuntimeException("카카오 사용자 정보를 가져올 수 없습니다.");
        }
        System.out.println("카카오 사용자 정보: " + kakaoUser.getEmail());

        // 이메일로 기존 회원 조회
        KakaoUserDto existingUser = userMapper.getUserByEmail(kakaoUser.getEmail());

        if (existingUser != null) {
            System.out.println("기존 회원 로그인: " + existingUser.getEmail());
            return existingUser; // 기존 회원이면 그대로 반환
        }

        // 신규 회원 가입
        userMapper.insertUser(kakaoUser);
        System.out.println("신규 회원 가입 완료: " + kakaoUser.getEmail());
        
        // 신규 회원의 user_id 가져오기
        KakaoUserDto newUser = userMapper.getUserByEmail(kakaoUser.getEmail());
        System.out.println("신규 회원 ID: " + newUser.getId());

        // 신규 회원의 친구 리스트 생성
        friendMapper.insertFriendList(newUser.getId());
        System.out.println("신규 회원의 친구 리스트 생성 완료");
        return kakaoUser;
    }
}
