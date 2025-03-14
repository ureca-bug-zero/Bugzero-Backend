package com.uplus.bugzerobackend.domain;

import com.uplus.bugzerobackend.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 추가
    @Column(name = "id", updatable = false)
    private Integer id;

//    @ElementCollection(fetch = FetchType.LAZY)
//    @Column(name = "friendList", nullable = false, columnDefinition = "TEXT")
////    private List<Integer> friendList;
//    private String friendList;

    @Convert(converter = JsonConverter.class)
    @Column(name = "friendList", nullable = false, columnDefinition = "JSON")
    private String friendList;


    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
