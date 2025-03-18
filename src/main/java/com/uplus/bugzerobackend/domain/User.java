package com.uplus.bugzerobackend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;
    private String name;
    private String email;
    private Integer weekScore;
    private Integer rank;
}
