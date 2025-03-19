package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.domain.Friend;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendMapper {
    Friend getFriendById(int userId);
    void deleteFriend(Map<String, Object> params);
}
