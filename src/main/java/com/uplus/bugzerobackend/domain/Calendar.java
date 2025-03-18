package com.uplus.bugzerobackend.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Calendar {
    private Integer id;
    private LocalDate date;
    private Integer userId;
}
