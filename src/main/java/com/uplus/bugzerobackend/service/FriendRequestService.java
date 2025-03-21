package com.uplus.bugzerobackend.service;


import com.uplus.bugzerobackend.dto.FriendListDto;
import com.uplus.bugzerobackend.dto.FriendRequestDto;
import com.uplus.bugzerobackend.dto.FriendResponseDto;
import com.uplus.bugzerobackend.mapper.FriendRequestMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FriendRequestService {
    private final FriendRequestMapper friendRequestMapper;

    public FriendRequestService(FriendRequestMapper friendRequestMapper) {
        this.friendRequestMapper = friendRequestMapper;
    }

    public void insertFriendRequest(String email, Integer senderId) {
        try {
//            log.debug("user search");

            Integer receiverId = friendRequestMapper.findUserIdByEmail(email);
            if (receiverId == null) {
                throw new EntityNotFoundException("해당 이메일을 가진 유저가 존재하지 않습니다.");
            }
            FriendRequestDto friendRequestDto = new FriendRequestDto();
            friendRequestDto.setSenderId(senderId);
            friendRequestDto.setReceiverId(receiverId);
//            log.debug("dto 추가");

            friendRequestMapper.insertFriendRequest(friendRequestDto);
        } catch(DataAccessException e){
            throw new IllegalStateException("친구 요청을 추가하는 중 오류가 발생하였습니다.");
        }
    }

    public void deleteFriendRequest(Integer receiverId, Integer senderId) {
        try {
            if (receiverId == null) {
                throw new IllegalArgumentException("친구 요청 응답에 receiverId가 null입니다.");
            }
            if (senderId == null) {
                throw new IllegalArgumentException("친구 요청 응답에 senderId가 null입니다.");
            }

            FriendResponseDto friendResponseDto = new FriendResponseDto();
            friendResponseDto.setReceiverId(receiverId);
            friendResponseDto.setSenderId(senderId);

            friendRequestMapper.deleteFriendRequest(friendResponseDto);
        } catch(DataAccessException e){
            throw new IllegalStateException("친구 요청 거절 처리 중 오류가 발생하였습니다.");
        }
    }

    public void updateFriendRequest(Integer receiverId, Integer senderId) {
        if (receiverId == null) {
            throw new IllegalArgumentException("친구 요청 응답에 receiverId가 null입니다.");
        }
        if (senderId == null) {
            throw new IllegalArgumentException("친구 요청 응답에 senderId가 null입니다.");
        }
        FriendResponseDto friendResponseDto = new FriendResponseDto();

        friendResponseDto.setReceiverId(receiverId);
        friendResponseDto.setSenderId(senderId);

        FriendResponseDto reverseDto = new FriendResponseDto();
        reverseDto.setReceiverId(senderId);
        reverseDto.setSenderId(receiverId);

        friendRequestMapper.updateFriendList(friendResponseDto);
        friendRequestMapper.updateFriendList(reverseDto);
    }
    
    public List<FriendListDto> getFriendRequests(Integer userId) throws Exception {
        try {
            return friendRequestMapper.findFriendRequestsByReceiverId(userId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }



}
