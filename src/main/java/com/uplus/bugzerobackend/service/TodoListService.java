package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.dto.TodoListPostDto;
import com.uplus.bugzerobackend.dto.TodoListUpdateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TodoListService {
    void insert(TodoListDto todoList);  // TodoList 추가
    Integer newTodoList(Integer userId, TodoListPostDto todoListPostDto);
    void update(Integer id, TodoListUpdateDto updateDto);  // TodoList 수정
    TodoListDto search(Integer id);     // TodoList 조회
    List<TodoListDto> searchAll(@Param("userId") Integer userId, @Param("date") LocalDate date);      // 모든 TodoList 조회
    void remove(Integer id);         // TodoList 삭제
    List<TodoListDto> searchByUserIdAndYearMonth(@Param("userId") Integer userId, @Param("yearMonth") String yearMonth);
    Double checkTodoList(Integer id);
    LocalDate getDate(Integer id);
}