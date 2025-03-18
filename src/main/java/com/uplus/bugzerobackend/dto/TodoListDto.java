package com.uplus.bugzerobackend.dto;

import java.time.LocalDate;
import com.uplus.bugzerobackend.domain.User;

import lombok.Data;

@Data
public class TodoListDto {
    private Integer id;           // TodoList의 고유 ID
    private LocalDate date;    // 날짜 추가
    private String content;    // 할 일 내용
    private boolean isMission; // 미션 여부 (필드명 수정)
    private boolean isChecked; // 체크 여부
    private String link;       // 관련 링크
    private Integer userId;    // userId 필드 추가
    private User user;         // 할 일을 작성한 유저 정보
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }    
}