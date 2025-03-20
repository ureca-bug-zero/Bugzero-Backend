package com.uplus.bugzerobackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoListUpdateDto {
    private String content; // 수정할 내용
    private String link;    // 수정할 링크
}
