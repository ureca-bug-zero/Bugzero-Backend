package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.mapper.TodoListDao;
import com.uplus.bugzerobackend.dto.TodoList;
import com.uplus.bugzerobackend.TodoListException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 
public class TodoListServiceImpl implements TodoListService {

    private final TodoListDao todoListDao;

    @Autowired
    public TodoListServiceImpl(TodoListDao todoListDao) {
        this.todoListDao = todoListDao;
    }

    // todo 등록
    @Override
    public void insert(TodoList todoList) {
        try {
            // 유저 정보 확인
            if (todoList.getUser() == null || todoList.getUser().getId() == null) {
                throw new TodoListException("유저 정보가 없습니다.");
            }

            // Long → Integer 변환 후 userId 설정
            Integer userId = todoList.getUser().getId().intValue();
            todoList.setUserId(userId); 

            // 같은 유저가 같은 날짜, 같은 내용의 Todo를 추가했는지 검사
            TodoList existingTodoList = todoListDao.searchByUserAndDateAndContent(
                userId, // Integer 타입으로 전달
                todoList.getDate(), 
                todoList.getContent()
            );

            if (existingTodoList != null) {
                throw new TodoListException("이미 존재하는 TodoList입니다.");
            }

            // 새로운 TodoList 저장
            todoListDao.insert(todoList);
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new TodoListException("TodoList 추가 중 오류 발생: " + e.getMessage());
        }
    }

    // todo 수정
    @Override
    public void update(TodoList todoList) {
        try {
            TodoList existingTodoList = todoListDao.search(todoList.getId());
            if (existingTodoList == null) {
                throw new TodoListException("수정할 TodoList를 찾을 수 없습니다.");
            }
            todoListDao.update(todoList);
        } catch (Exception e) {
            throw new TodoListException("TodoList 수정 중 오류 발생: " + e.getMessage());
        }
    }

    // todo 지정검색(id)
    @Override
    public TodoList search(Integer id) {
        try {
            TodoList todoList = todoListDao.search(id);
            if (todoList == null) {
                throw new TodoListException("요청한 TodoList를 찾을 수 없습니다.");
            }
            return todoList;
        } catch (Exception e) {
            throw new TodoListException("TodoList 조회 중 오류 발생: " + e.getMessage());
        }
    }

    // todo 전체검색
    @Override
    public List<TodoList> searchAll() {
        try {
            return todoListDao.searchAll();
        } catch (Exception e) {
            throw new TodoListException("TodoList 목록 조회 중 오류 발생: " + e.getMessage());
        }
    }

    // todo 삭제
    @Override
    public void remove(Integer id) {
        try {
        	TodoList existingTodoList = todoListDao.search(id);
        	if(existingTodoList == null) {
        		throw new TodoListException("삭제할 Todolist를 찾을 수 없습니다.");
        	}
            todoListDao.remove(id);
        } catch (Exception e) {
            throw new TodoListException("TodoList 삭제 중 오류 발생: " + e.getMessage());
        }
    }
    
}
