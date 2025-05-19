package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.domain.Friend;
import com.uplus.bugzerobackend.domain.User;
import com.uplus.bugzerobackend.dto.FriendInfoDto;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FriendMapper {
	FriendInfoDto getFriendUserById(int userId);
    Friend getFriendById(int userId);
    int checkFriendExistence(Map<String, Object> params);
    void deleteFriend(Map<String, Object> params);
    void insertFriendList(@Param("userId") int userId);
}
