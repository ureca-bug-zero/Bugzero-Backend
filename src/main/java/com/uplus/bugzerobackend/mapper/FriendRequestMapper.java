package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.dto.FriendRequestDto;
import com.uplus.bugzerobackend.dto.FriendResponseDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface FriendRequestMapper {
    Integer findUserIdByEmail(@Param("email") String email);
    void insertFriendRequest(FriendRequestDto friendRequestDto);
    void deleteFriendRequest(FriendResponseDto friendResponseDto);
    void updateFriendList(FriendResponseDto friendResponseDto);
    List<FriendResponseDto> findFriendRequestsByReceiverId(@Param("receiverId") Integer receiverId);
}
