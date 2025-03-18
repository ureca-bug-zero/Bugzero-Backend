package com.uplus.bugzerobackend.controller;

import com.uplus.bugzerobackend.dto.TodoList;
import com.uplus.bugzerobackend.service.TodoListService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolists")
//@Slf4j
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
//    	log.debug("TodoListController....." );
    	System.out.println("................................");
        this.todoListService = todoListService;
    }

    // TodoList 추가
    @PostMapping
    public ResponseEntity<String> createTodoList(@RequestBody TodoList todoList) {
//    	log.debug("createTodoList todoList:{}", todoList);
    	System.out.println("todoList:"+todoList);
        todoListService.insert(todoList);
        return ResponseEntity.ok("TodoList가 성공적으로 생성되었습니다.");
    }

    // TodoList 조회
    @GetMapping("/{id}")
    public ResponseEntity<TodoList> getTodoList(@PathVariable Long id) {
        TodoList todoList = todoListService.search(id);
        return ResponseEntity.ok(todoList);
    }

    // 모든 TodoList 조회
    @GetMapping
    public ResponseEntity<List<TodoList>> getAllTodoLists() {
        List<TodoList> todoLists = todoListService.searchAll();
        return ResponseEntity.ok(todoLists);
    }

    // TodoList 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodoList(@PathVariable Long id, @RequestBody TodoList todoList) {
        todoList.setId(id);
        todoListService.update(todoList);
        return ResponseEntity.ok("TodoList가 성공적으로 수정되었습니다.");
    }

    // TodoList 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodoList(@PathVariable Long id) {
        todoListService.remove(id);
        return ResponseEntity.ok("TodoList가 성공적으로 삭제되었습니다.");
    }
}
