package com.uplus.bugzerobackend.service;

import com.uplus.bugzerobackend.dto.TodoListPostDto;
import com.uplus.bugzerobackend.mapper.TodoListMapper;
import com.uplus.bugzerobackend.mapper.UserMapper;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.dto.TodoListUpdateDto;
import com.uplus.bugzerobackend.domain.User;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service 
public class TodoListServiceImpl implements TodoListService {

    private final TodoListMapper todoListDao;
    @Autowired
    private TodoListMapper todoListMapper;

    @Autowired
    public TodoListServiceImpl(TodoListMapper todoListDao, CalendarService calendarService) {
        this.todoListDao = todoListDao;
        this.calendarService = calendarService;
    }
    @Autowired
    private UserMapper userMapper;

    private final CalendarService calendarService;
    
    //todo등록
    @Override
    public void insert(TodoListDto todoListDto) {
        if (todoListDto.getUserId() == null) {
            throw new EntityNotFoundException("유저 정보가 없습니다.");
        }

        // MyBatis를 사용해 유저 정보 조회
        User user = userMapper.getUserById(todoListDto.getUserId());
        if (user == null) {
            throw new EntityNotFoundException("해당 userId를 가진 유저가 없습니다.");
        }

        // User 정보 설정
        todoListDto.setUser(user);
        todoListDao.insert(todoListDto);
    }

    @Override
    public Integer newTodoList(Integer userId, TodoListPostDto todoListPostDto) {
        try {
            if(userId == null) {
                throw new EntityNotFoundException("userId가 없습니다.");
            }
            Map<String, Object> todoMap = new HashMap<>();
            todoMap.put("userId", userId);
            todoMap.put("date", todoListPostDto.getDate());
            todoMap.put("content", todoListPostDto.getContent());
            todoMap.put("link", todoListPostDto.getLink());
            todoListMapper.newTodoList(todoMap);

            Object getId = todoMap.get("id");
            BigInteger big = new BigInteger(String.valueOf(getId));
            Integer todoId = big.intValue();
            return todoId;
        } catch(Exception e) {
            throw new IllegalStateException("TodoList 추가 중 오류 발생: " + e.getMessage());
        }
    }

    // todo 수정
    @Override
    public void update(Integer id, TodoListUpdateDto updateDto) {
        try {
            TodoListDto existingTodoList = todoListDao.search(id);
            if (existingTodoList == null) {
                throw new EntityNotFoundException("수정할 TodoList를 찾을 수 없습니다.");
            }

            // content와 link만 업데이트
            if (updateDto.getContent() != null && !updateDto.getContent().trim().isEmpty()) {
                existingTodoList.setContent(updateDto.getContent());
            }
            if (updateDto.getLink() != null && !updateDto.getLink().trim().isEmpty()) {
                existingTodoList.setLink(updateDto.getLink());
            }

            todoListDao.update(existingTodoList);
        } catch (Exception e) {
            throw new IllegalStateException("TodoList 수정 중 오류 발생: " + e.getMessage());
        }
    }




    // todo 지정검색(todolist id로 검색)
    @Override
    public TodoListDto search(Integer id) {
        try {
            TodoListDto todoList = todoListDao.search(id);
            if (todoList == null) {
                throw new EntityNotFoundException("요청한 TodoList를 찾을 수 없습니다.");
            }
            return todoList;
        } catch (Exception e) {
            throw new IllegalStateException("TodoList 조회 중 오류 발생: " + e.getMessage());
        }
    }

    // 특정 유저의 todo 전체검색
    @Override
    public List<TodoListDto> searchAll(Integer userId, LocalDate date) {
        // 1. userId가 존재하는지 확인
        boolean userExists = todoListDao.existsByUserId(userId);
        if (!userExists) {
            throw new NoSuchElementException("해당 userId가 존재하지 않습니다: " + userId);
        }

        // 2. 존재하면 해당 유저의 투두 리스트 조회
        List<TodoListDto> todoList = todoListDao.searchAll(userId, date);

        // 3. 투두 리스트가 없으면 빈 리스트 반환
        return todoList != null ? todoList : Collections.emptyList();
    }

    //특정 유저의 특정 년-월 투두 검색
    @Override
    public List<TodoListDto> searchByUserIdAndYearMonth(Integer userId, String yearMonth) {
        List<TodoListDto> todoList = todoListDao.findByUserIdAndYearMonth(userId, yearMonth);
        
        // 결과가 null이면 빈 리스트 반환 (예외 발생 X)
        return todoList != null ? todoList : Collections.emptyList();
    }
    
    // todo 삭제
    @Override
    public void remove(Integer id) {
        try {
        	TodoListDto existingTodoList = todoListDao.search(id);
        	if(existingTodoList == null) {
        		throw new EntityNotFoundException("삭제할 Todolist를 찾을 수 없습니다.");
        	}
            todoListDao.remove(id);
        } catch (Exception e) {
            throw new IllegalStateException("TodoList 삭제 중 오류 발생: " + e.getMessage());
        }
    }

    @Override
    public Double checkTodoList(Integer id) {
        try {
            TodoListDto todo = todoListMapper.search(id);
            if (todo == null) {
                throw new EntityNotFoundException("해당 ID의 TodoList가 없습니다.");
            }

            todoListMapper.checkTodoList(id);

            List<TodoListDto> todoLists = todoListMapper.searchAll(todo.getUserId(), todo.getDate());

            // 달성률 계산
            Map<Integer, Double> progressMap = calendarService.processProgress(todoLists);
            double progress = progressMap.getOrDefault(todo.getDate().getDayOfMonth(), 0.0);

            return Math.round(progress * 100.0) / 100.0;
        } catch (DataAccessException e) {
            throw new IllegalStateException("체크리스트 처리 중 오류가 발생하였습니다.");
        }
    }

    @Override
    public LocalDate getDate(Integer id) {
        LocalDate date = todoListMapper.getDateById(id);

        return date;
    }
    
}
