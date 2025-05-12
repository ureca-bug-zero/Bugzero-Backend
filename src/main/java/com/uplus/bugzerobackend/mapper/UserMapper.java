package com.uplus.bugzerobackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.uplus.bugzerobackend.domain.User;
import com.uplus.bugzerobackend.dto.KakaoUserDto;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.dto.UserDto;

@Mapper
public interface UserMapper {
  User getUserById(Integer userId);
  List<UserDto> findAll();
  KakaoUserDto getUserByEmail(@Param("email") String email);
  void insertUser(KakaoUserDto user);
  void update(UserDto userList);  // userList 수정
  void updateWeekScore(UserDto user);
}
