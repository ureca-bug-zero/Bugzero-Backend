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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 추가
    @Column(name = "id", updatable = false, columnDefinition = "INTEGER")
//    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "isMission", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isMission;

    @Column(name = "isChecked", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isChecked;

    @Column(name = "link", length=512)
    private String link;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "INTEGER")
//    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // userId 필드는 DB에 저장하지 않고, 객체에서만 사용
    @Transient
    private Integer userId;

    // user 객체에서 userId 가져오기
    public Integer getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    // userId를 설정하면 user 객체도 업데이트
    public void setUserId(Integer userId) {
        this.userId = userId;
        if (userId != null) {
            this.user = new User();
            this.user.setId(userId);
        } else {
            this.user = null;
        }
    }

    // `User` 객체를 설정하는 메서드 (setUser)
    public void setUser(User user) {
        this.user = user;
    }

    // `User` 객체를 가져오는 메서드 (getUser)
    public User getUser() {
        return this.user;
    }

    // isMission getter 추가
    public boolean getIsMission() {
        return isMission;
    }
}
