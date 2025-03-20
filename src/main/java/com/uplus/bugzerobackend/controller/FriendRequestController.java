package com.uplus.bugzerobackend.controller;

import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.dto.FriendRequestDto;
import com.uplus.bugzerobackend.service.FriendRequestService;
import com.uplus.bugzerobackend.service.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtTokenService jwtTokenService;


    @PostMapping("/request")
    public ResponseEntity<ApiResponseDto<FriendRequestDto>> insertFriendRequest(
            HttpServletRequest request, @RequestParam String email) {
        Integer senderId = jwtTokenService.getUserId(request);

        friendRequestService.insertFriendRequest(email, senderId);

        return ResponseEntity.ok(ApiResponseDto.success("친구 요청을 성공하였습니다.", null));
    }

    @PostMapping("/response/refuse")
    public ResponseEntity<ApiResponseDto<Void>> refuseFriendRequest(
            HttpServletRequest request, @RequestParam Integer senderId) {
        Integer receiverId = jwtTokenService.getUserId(request);
        try {
            friendRequestService.deleteFriendRequest(receiverId, senderId);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("친구 거절 후 friendRequest DB 업데이트에 실패하였습니다.");
        }
        return ResponseEntity.ok(ApiResponseDto.success("친구 요청 거절에 성공하였습니다.", null));
    }

    @PostMapping("/response/accept")
    public ResponseEntity<ApiResponseDto<Void>> acceptFriendRequests(
            HttpServletRequest request, @RequestParam Integer senderId) {
        Integer receiverId = jwtTokenService.getUserId(request);
        friendRequestService.updateFriendRequest(receiverId, senderId);
        try {
            friendRequestService.deleteFriendRequest(receiverId, senderId);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("친구 수락 후 friendRequest DB 업데이트에 실패하였습니다.");
        }
        return ResponseEntity.ok(ApiResponseDto.success("친구 요청 수락에 성공하였습니다.", null));
    }
}
