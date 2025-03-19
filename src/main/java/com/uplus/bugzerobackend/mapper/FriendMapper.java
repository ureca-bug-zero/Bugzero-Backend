package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.domain.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FriendMapper {
    Friend getFriendById(int userId);
    void insertFriendList(@Param("userId") int userId);
}
