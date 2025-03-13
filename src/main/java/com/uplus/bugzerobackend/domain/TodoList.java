package com.uplus.bugzerobackend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 추가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "isMission", nullable = false)
    private boolean isMission;

    @Column(name = "isChecked", nullable = false)
    private boolean isChecked;

    @Column(name = "link", nullable = true)
    private String link;
}
