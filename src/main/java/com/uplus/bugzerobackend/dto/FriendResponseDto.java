package com.uplus.bugzerobackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendResponseDto {
    private Integer receiverId; // 응답 보내는 유저 Id
    private Integer senderId; // 친구 요청한 유저 Id
}
