package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.dto.TodoListUpdateDto;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TodoListService {
    void insert(TodoListDto todoList);  // TodoList 추가
    void update(Integer id, TodoListUpdateDto updateDto);  // TodoList 수정
    TodoListDto search(Integer id);     // TodoList 조회
    List<TodoListDto> searchAll(@Param("userId") Integer userId, @Param("date") LocalDate date);      // 모든 TodoList 조회
    void remove(Integer id);         // TodoList 삭제

    void checkTodoList(Integer id);
}
