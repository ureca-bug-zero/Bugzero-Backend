package com.uplus.bugzerobackend.controller;

import com.uplus.bugzerobackend.dto.ApiResponseDto;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.dto.TodoListUpdateDto;
import com.uplus.bugzerobackend.service.JwtTokenService;
import com.uplus.bugzerobackend.service.TodoListService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todolist")
@RequiredArgsConstructor
//@Slf4j
public class TodoListController {
    private final TodoListService todoListService;
    private final JwtTokenService jwtTokenService;

    // TodoList 추가
    @PostMapping
    public ResponseEntity<ApiResponseDto<Map<String, Integer>>> createTodoList(@RequestBody TodoListDto todoList) {
//    	log.debug("createTodoList todoList:{}", todoList);
    	System.out.println("todoList:" + todoList);
        todoListService.insert(todoList);

        return ResponseEntity.ok(ApiResponseDto.success("TodoList가 성공적으로 생성되었습니다.", Map.of("id", todoList.getId())));
    }

//    // TodoList 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<TodoListDto> getTodoList(@PathVariable("id") Integer id) {
//        TodoListDto todoList = todoListService.search(id);
//        return ResponseEntity.ok(todoList);
//    }

    // 모든 TodoList 조회
    @GetMapping("/user/request")
    public ResponseEntity<ApiResponseDto<List<TodoListDto>>> getAllTodoLists(
        HttpServletRequest request,
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    	
    	Integer userId = jwtTokenService.getUserId(request);
        List<TodoListDto> todoList = todoListService.searchAll(userId, date);
        return ResponseEntity.ok(ApiResponseDto.success("Todo List 조회를 성공하였습니다.",todoList));
    }

    // TodoList 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> updateTodoList(@PathVariable("id") Integer id, @RequestBody TodoListUpdateDto updateDto) {
    	todoListService.update(id, updateDto);
        return ResponseEntity.ok(ApiResponseDto.success("TodoList가 성공적으로 수정되었습니다.", null));
    }

    // TodoList 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteTodoList(@PathVariable("id") Integer id) {
        todoListService.remove(id);
        return ResponseEntity.ok(ApiResponseDto.success("TodoList가 성공적으로 삭제되었습니다.", null));
    }

    @PostMapping("/check/{id}")
    public ResponseEntity<ApiResponseDto<Void>> checkTodoList(@PathVariable("id") Integer id) {
        todoListService.checkTodoList(id);
        return ResponseEntity.ok(ApiResponseDto.success("투두 리스트 체크를 성공하였습니다.", null));
    }
}
