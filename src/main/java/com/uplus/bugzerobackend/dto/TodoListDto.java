package com.uplus.bugzerobackend.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uplus.bugzerobackend.domain.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TodoListDto {
    private Integer id;           // TodoList의 고유 ID
    private LocalDate date;    // 날짜 추가
    private String content;    // 할 일 내용
    private boolean isMission; // 미션 여부 (필드명 수정)
    private boolean isChecked; // 체크 여부
    private String link;       // 관련 링크
    private Integer userId;    // userId 필드 추가
    
    @JsonIgnore  // JSON 응답에서 user 필드 제외
    private User user;         // 할 일을 작성한 유저 정보
}