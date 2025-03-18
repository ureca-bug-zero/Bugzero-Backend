package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.dto.TodoList;
import java.util.List;

public interface TodoListService {
    void insert(TodoList todoList);  // TodoList 추가
    void update(TodoList todoList);  // TodoList 수정
    TodoList search(Integer id);        // TodoList 조회
    List<TodoList> searchAll();      // 모든 TodoList 조회
    void remove(Integer id);            // TodoList 삭제
}
