package com.uplus.bugzerobackend.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TodoList {
    private Integer id;
    private LocalDate date;
    private String content;
    private boolean isMission;
    private boolean isChecked;
    private String link;
    private Integer userId;
}
