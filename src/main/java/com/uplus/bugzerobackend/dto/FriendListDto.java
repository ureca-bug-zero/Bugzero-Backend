package com.uplus.bugzerobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendListDto {
    private Integer friendId;
    private String friendName;
    private String friendemail;
}
