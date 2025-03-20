package com.uplus.bugzerobackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uplus.bugzerobackend.domain.User;
import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.mapper.UserMapper;
import com.uplus.bugzerobackend.service.JwtTokenService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserMapper userMapper;
	private final JwtTokenService jwtTokenService;
	
	@GetMapping("/info")
	public ResponseEntity<ApiResponseDto<User>> getUser(HttpServletRequest request){
		Integer userId = jwtTokenService.getUserId(request);
		User user = userMapper.getUserById(userId);
        if(user == null) {
            throw new EntityNotFoundException("존재하지 않는 유저입니다.");
        }		
		return ResponseEntity.ok(ApiResponseDto.success("유저 정보 조회 성공", user));
		
	}
}
