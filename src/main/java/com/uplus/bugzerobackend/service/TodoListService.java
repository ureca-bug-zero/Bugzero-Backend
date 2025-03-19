package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.dto.TodoListDto;
import java.util.List;

public interface TodoListService {
    void insert(TodoListDto todoList);  // TodoList 추가
    void update(TodoListDto todoList);  // TodoList 수정
    TodoListDto search(Integer id);     // TodoList 조회
    List<TodoListDto> searchAll();      // 모든 TodoList 조회
    void remove(Integer id);         // TodoList 삭제
}
