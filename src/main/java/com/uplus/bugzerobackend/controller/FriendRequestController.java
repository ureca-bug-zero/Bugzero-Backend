package com.uplus.bugzerobackend.controller;

import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.dto.FriendRequestDto;
import com.uplus.bugzerobackend.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/response/refuse")
    public ResponseEntity<ApiResponseDto<Void>> refuseFriendRequest(
            @RequestParam Integer receiverId, @RequestParam Integer senderId) {
        friendRequestService.deleteFriendRequest(receiverId, senderId);

        return ResponseEntity.ok(ApiResponseDto.success("친구 요청 거절에 성공하였습니다.", null));
    }

    @PostMapping("/response/accept")
    public ResponseEntity<ApiResponseDto<Void>> acceptFriendRequests(
            @RequestParam Integer receiverId, @RequestParam Integer senderId) {
        friendRequestService.updateFriendRequest(receiverId, senderId);
        try {
            friendRequestService.deleteFriendRequest(receiverId, senderId);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("친구 수락 후 friendRequest DB 업데이트에 실패하였습니다.");
        }
        return ResponseEntity.ok(ApiResponseDto.success("친구 요청 수락에 성공하였습니다.", null));
    }
}
