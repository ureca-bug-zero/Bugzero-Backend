package com.uplus.bugzerobackend.controller;

import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.dto.FriendRequestDto;
import com.uplus.bugzerobackend.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponseDto<FriendRequestDto>> insertFriendRequest(
            @RequestParam String email, @RequestParam Integer senderId) {
        friendRequestService.insertFriendRequest(email, senderId);

        return ResponseEntity.ok(ApiResponseDto.success("친구 요청을 성공하였습니다.", null));
    }
}
