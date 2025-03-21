package com.uplus.bugzerobackend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uplus.bugzerobackend.domain.Friend;
import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.mapper.FriendMapper;
import com.uplus.bugzerobackend.service.JwtTokenService;
import com.uplus.bugzerobackend.service.TodoListService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friend/todolist")
@RequiredArgsConstructor

public class FriendTodoListController {
	private final FriendMapper friendMapper;
	private final TodoListService todoListService;
	private final JwtTokenService jwtTokenService;
	
    // 모든 TodoList 조회
    @GetMapping("/{friendId}")
    public ResponseEntity<ApiResponseDto<List<TodoListDto>>> getAllTodoLists(
            HttpServletRequest request,
            @PathVariable("friendId") Integer friendId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
    	Integer userId = jwtTokenService.getUserId(request);
        Friend friend = friendMapper.getFriendById(userId);
        boolean isContain = friend.getFriendList().contains(friendId);
        
        if(isContain) {
            throw new EntityNotFoundException("친구가 아닙니다.");
        }
        
        List<TodoListDto> todoList = todoListService.searchAll(friendId, date);
        return ResponseEntity.ok(ApiResponseDto.success("Todo List 조회를 성공하였습니다.",todoList));
    }
}
