package com.uplus.bugzerobackend.controller;


import com.uplus.bugzerobackend.domain.User;
import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.dto.FriendListDto;
import com.uplus.bugzerobackend.mapper.UserMapper;
import com.uplus.bugzerobackend.service.FriendService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final UserMapper userMapper;
    private final FriendService friendService;


    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDto<List<FriendListDto>>> getFriends(@PathVariable Integer userId) {
        // 유저 존재 여부 확인
        User user = userMapper.getUserById(userId);
        if(user == null) {
            throw new EntityNotFoundException("존재하지 않는 유저입니다.");
        }

        // 친구 리스트 가져오기
        List<FriendListDto> friendList = friendService.getFriend(userId);

        return ResponseEntity.ok(ApiResponseDto.success("친구 리스트 조회 성공", friendList.isEmpty() ? null : friendList));
    }
}
