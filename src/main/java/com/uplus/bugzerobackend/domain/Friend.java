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
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 추가
    @Column(name = "id", updatable = false)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "friendList", nullable = false)
    private List<Integer> friendList;
}
