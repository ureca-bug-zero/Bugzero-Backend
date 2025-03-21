package com.uplus.bugzerobackend.controller;

import java.util.List;
import java.util.Map;

import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.service.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uplus.bugzerobackend.dto.CalendarRequestDto;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.service.CalendarService;
import com.uplus.bugzerobackend.service.TodoListService;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class   CalendarController {

    private final TodoListService todoListService;
    private final CalendarService calendarService;
    private final JwtTokenService jwtTokenService;


    // 월별 투두 개수 조회
    @GetMapping
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> getMonthlyProgress(
            HttpServletRequest request,  @RequestParam(name="yearMonth") String yearMonth) {
        Integer userId = jwtTokenService.getUserId(request);
        List<TodoListDto> todoLists = todoListService.searchByUserIdAndYearMonth(userId, yearMonth);

        Map<String, Object> response = calendarService.processMonthly(todoLists, yearMonth);
        return ResponseEntity.ok(ApiResponseDto.success("캘린더 조회를 성공하였습니다.", response));
    }
}