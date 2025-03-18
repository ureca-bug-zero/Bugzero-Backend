package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserById(Integer userId);
}
