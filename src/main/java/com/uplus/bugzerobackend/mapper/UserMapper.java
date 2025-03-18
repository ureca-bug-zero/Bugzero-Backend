package com.uplus.bugzerobackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.uplus.bugzerobackend.dto.UserDto;

@Mapper
public interface UserMapper {
	List<UserDto> findAll();
}
