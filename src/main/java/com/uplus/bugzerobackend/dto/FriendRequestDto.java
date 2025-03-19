package com.uplus.bugzerobackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestDto {
    private Integer senderId;
    private Integer receiverId;
}
