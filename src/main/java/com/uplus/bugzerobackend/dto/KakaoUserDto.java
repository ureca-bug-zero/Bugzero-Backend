package com.uplus.bugzerobackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KakaoUserDto {
    private String name;	//kakao nickname
    private String email;	//kakao email
    private Integer weekScore;
    private Integer rank;

    public KakaoUserDto() {
        this.weekScore = 0; // 기본값 설정
        this.rank = 0;      // 기본값 설정
    }
}
