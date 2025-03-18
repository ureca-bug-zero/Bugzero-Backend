package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.domain.Friend;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface FriendMapper {
    Friend searchFriendList(int id) throws SQLException;
}
