package com.uplus.bugzerobackend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 추가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    private int status;

    @ManyToOne
    @JoinColumn(name = "senderId")  // 보낸 사람
    private User senderId;

    @ManyToOne
    @JoinColumn(name = "receiverId")  // 받은 사람
    private User receiverId;
}
