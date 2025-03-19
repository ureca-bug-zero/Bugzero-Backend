package com.uplus.bugzerobackend.service;

import java.util.List;

import com.uplus.bugzerobackend.dto.UserDto;
import com.uplus.bugzerobackend.mapper.UserMapper;

public class UserService {
	
	private final UserMapper userMapper;
	
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	// 모든 유저 정보 가져옴
	public List<UserDto> getAllUsers(){
		return userMapper.findAll();
	}

}
