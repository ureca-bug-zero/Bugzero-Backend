package com.uplus.bugzerobackend.controller;

import java.util.List;
import java.util.Map;

import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.service.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uplus.bugzerobackend.dto.CalendarRequestDto;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.service.CalendarService;
import com.uplus.bugzerobackend.service.TodoListService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class   CalendarController {

    private final TodoListService todoListService;
    private final CalendarService calendarService;
    private final JwtTokenService jwtTokenService;


    // 월별 투두 개수 조회
    @PostMapping("/monthly")
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> getMonthlyProgress(
            HttpServletRequest request,  @RequestBody CalendarRequestDto calendarRequestDto) {
        Integer userId = jwtTokenService.getUserId(request);
        List<TodoListDto> todoLists = todoListService.searchByUserIdAndYearMonth(userId, calendarRequestDto.getYearMonth());

        Map<String, Object> response = calendarService.processMonthly(todoLists, calendarRequestDto);
        return ResponseEntity.ok(ApiResponseDto.success("캘린더 조회를 성공하였습니다.", response));
    }


    // 특정 날짜 투두 달성 개수 조회
    @PostMapping("/daily")
    public ResponseEntity<Map<String, Object>> getDailyTodoStatus(HttpServletRequest request, @RequestBody CalendarRequestDto calendarRequestDto) {
        Integer userId = jwtTokenService.getUserId(request);
        List<TodoListDto> todoLists = todoListService.searchAll(userId, calendarRequestDto.getDate());

        Map<String, Object> response = calendarService.processDaily(todoLists, calendarRequestDto);
        return ResponseEntity.ok(response);
    }

}