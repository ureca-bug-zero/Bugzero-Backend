package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.dto.TodoListDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TodoListMapper {
    void insert(TodoListDto todoList);  					 // TodoList 추가
    void update(TodoListDto todoList); 						 // TodoList 수정
    TodoListDto search(Integer id);       					 // TodoList 조회
    List<TodoListDto> searchAll(@Param("userId") Integer userId, @Param("date") LocalDate date); // 모든 TodoList 조회
    void remove(Integer id);           						 // TodoList 삭제
    List<TodoListDto> findByUserIdAndYearMonth(@Param("userId") Integer userId, @Param("yearMonth") String yearMonth); //년-월별 투두조회

    // 같은 유저가 같은 날짜, 같은 내용의 Todo를 추가했는지 확인
    TodoListDto searchByUserAndDateAndContent(
        @Param("userId") Integer userId, 
        @Param("date") LocalDate date, 
        @Param("content") String content
    );

    void checkTodoList(Integer id);
    boolean existsByUserId(@Param("userId") Integer userId);
}
