package com.uplus.bugzerobackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uplus.bugzerobackend.domain.Friend;
import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.dto.CalendarRequestDto;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.mapper.FriendMapper;
import com.uplus.bugzerobackend.service.CalendarService;
import com.uplus.bugzerobackend.service.JwtTokenService;
import com.uplus.bugzerobackend.service.TodoListService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friend/calendar")
@RequiredArgsConstructor
public class FriendCalendarController {
	
	private final FriendMapper friendMapper;
    private final TodoListService todoListService;
    private final CalendarService calendarService;
    private final JwtTokenService jwtTokenService;

    // 월별 투두 개수 조회
    @PostMapping("/{friendId}")
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> getMonthlyProgress(
            HttpServletRequest request,  @PathVariable("friendId") Integer friendId, @RequestBody CalendarRequestDto calendarRequestDto) {
        Integer userId = jwtTokenService.getUserId(request);
        Friend friend = friendMapper.getFriendById(userId);
        
        boolean isContain = friend.getFriendList().contains(friendId);
        if(isContain) {
            throw new EntityNotFoundException("친구가 아닙니다.");
        }
        
        List<TodoListDto> todoLists = todoListService.searchByUserIdAndYearMonth(friendId, calendarRequestDto.getYearMonth());

        Map<String, Object> response = calendarService.processMonthly(todoLists, calendarRequestDto);
        return ResponseEntity.ok(ApiResponseDto.success("캘린더 조회를 성공하였습니다.", response));
    }
}
