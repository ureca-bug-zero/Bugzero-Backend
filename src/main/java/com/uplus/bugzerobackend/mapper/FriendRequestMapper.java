package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.dto.FriendRequestDto;
import org.apache.ibatis.annotations.Param;

public interface FriendRequestMapper {
    Integer findUserIdByEmail(@Param("email") String email);
    void insertFriendRequest(FriendRequestDto friendRequestDto);
}
