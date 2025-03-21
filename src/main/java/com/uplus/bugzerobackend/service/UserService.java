package com.uplus.bugzerobackend.service;

import java.util.List;

import com.uplus.bugzerobackend.domain.User;
import com.uplus.bugzerobackend.dto.UserDto;
import com.uplus.bugzerobackend.mapper.UserMapper;

import jakarta.persistence.EntityNotFoundException;

public class UserService {
	
	private final UserMapper userMapper;
	
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	// 모든 유저 정보 가져옴
	public List<UserDto> getAllUsers(){
		return userMapper.findAll();
	}
	
	public User getUser(Integer userId) {
        if (userId == null) {
            throw new EntityNotFoundException("해당 user가 존재하지 않습니다.");
        }
		return userMapper.getUserById(userId);
	}

}
