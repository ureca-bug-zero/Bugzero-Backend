package com.uplus.bugzerobackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TodoListPostDto {
    private LocalDate date;
    private String content;
    private String link = "";
}
