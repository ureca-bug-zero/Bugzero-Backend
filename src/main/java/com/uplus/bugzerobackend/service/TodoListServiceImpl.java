package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.mapper.TodoListMapper;
import com.uplus.bugzerobackend.mapper.UserMapper;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.TodoListException;
import com.uplus.bugzerobackend.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service 
public class TodoListServiceImpl implements TodoListService {

    private final TodoListMapper todoListDao;

    @Autowired
    public TodoListServiceImpl(TodoListMapper todoListDao) {
        this.todoListDao = todoListDao;
    }
    @Autowired
    private UserMapper userMapper;
    
    //todo등록
    @Override
    public void insert(TodoListDto todoListDto) {
        if (todoListDto.getUserId() == null) {
            throw new TodoListException("유저 정보가 없습니다.");
        }

        // MyBatis를 사용해 유저 정보 조회
        User user = userMapper.getUserById(todoListDto.getUserId());
        if (user == null) {
            throw new TodoListException("해당 userId를 가진 유저가 없습니다.");
        }

        // User 정보 설정
        todoListDto.setUser(user);
        todoListDao.insert(todoListDto);
    }

    // todo 수정
    @Override
    public void update(TodoListDto todoList) {
        try {
            if (todoList.getId() == null || todoList.getUserId() == null) {
                throw new TodoListException("TodoList ID 또는 User ID가 필요합니다.");
            }
            TodoListDto existingTodoList = todoListDao.search(todoList.getId());
            if (existingTodoList == null) {
                throw new TodoListException("수정할 TodoList를 찾을 수 없습니다.");
            }
            todoListDao.update(todoList);	// Dto 객체 자체를 전달
        } catch (Exception e) {
            throw new TodoListException("TodoList 수정 중 오류 발생: " + e.getMessage());
        }
    }


    // todo 지정검색(todolist id로 검색)
    @Override
    public TodoListDto search(Integer id) {
        try {
            TodoListDto todoList = todoListDao.search(id);
            if (todoList == null) {
                throw new TodoListException("요청한 TodoList를 찾을 수 없습니다.");
            }
            return todoList;
        } catch (Exception e) {
            throw new TodoListException("TodoList 조회 중 오류 발생: " + e.getMessage());
        }
    }

    // 특정 유저의 todo 전체검색
    @Override
    public List<TodoListDto> searchAll(Integer id) {
        List<TodoListDto> todoList = todoListDao.searchAll(id);
        
        // 빈 리스트 반환 (예외 던지지 않음)
        return todoList != null ? todoList : Collections.emptyList();
    }

    // todo 삭제
    @Override
    public void remove(Integer id) {
        try {
        	TodoListDto existingTodoList = todoListDao.search(id);
        	if(existingTodoList == null) {
        		throw new TodoListException("삭제할 Todolist를 찾을 수 없습니다.");
        	}
            todoListDao.remove(id);
        } catch (Exception e) {
            throw new TodoListException("TodoList 삭제 중 오류 발생: " + e.getMessage());
        }
    }
    
}
