package com.uplus.bugzerobackend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequest {
    private Integer id;
    private int status;        // 요청 상태 (0: 대기, 1: 수락, 2: 거절)
    private Integer senderId;
    private Integer receiverId;
}