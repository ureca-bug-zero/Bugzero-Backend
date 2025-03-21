package com.uplus.bugzerobackend.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Friend {
    private Integer id;
    private Integer userId;
    private List<Integer> friendList;
}