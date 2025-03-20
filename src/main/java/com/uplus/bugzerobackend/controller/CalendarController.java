package com.uplus.bugzerobackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CalendarController {

    private final TodoListService todoListService;
    private final CalendarService calendarService;

    @Autowired
    public CalendarController(TodoListService todoListService, CalendarService calendarService) {
        this.todoListService = todoListService;
        this.calendarService = calendarService;
    }

    // 월별 투두 개수 조회
    @PostMapping("/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyProgress(@RequestBody CalendarRequestDto request) {
        List<TodoListDto> todoLists = todoListService.searchByUserIdAndYearMonth(request.getUserId(), request.getYearMonth());

        Map<String, Object> response = calendarService.processMonthly(todoLists, request);
        return ResponseEntity.ok(response);
    }


    // 특정 날짜 투두 달성 개수 조회
    @PostMapping("/daily")
    public ResponseEntity<Map<String, Object>> getDailyTodoStatus(@RequestBody CalendarRequestDto request) {
        List<TodoListDto> todoLists = todoListService.searchAll(request.getUserId(), request.getDate());

        Map<String, Object> response = calendarService.processDaily(todoLists, request);
        return ResponseEntity.ok(response);
    }

}