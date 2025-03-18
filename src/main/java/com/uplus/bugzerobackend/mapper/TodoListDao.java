package com.uplus.bugzerobackend.mapper;

import com.uplus.bugzerobackend.dto.TodoList;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TodoListDao {
//    void insert(TodoList todoList);  // TodoList 추가
//    void update(TodoList todoList);  // TodoList 수정
//    TodoList search(Integer id);        // TodoList 조회
//    List<TodoList> searchAll();      // 모든 TodoList 조회
//    void remove(Integer id);            // TodoList 삭제
    void insert(TodoList todoList);
    
    void update(
        @Param("id") Integer id, 
        @Param("userId") Integer userId, 
        @Param("date") LocalDate date,
        @Param("content") String content,
        @Param("isMission") Boolean isMission,
        @Param("isChecked") Boolean isChecked,
        @Param("link") String link
    );  

    TodoList search(@Param("id") Integer id);
    List<TodoList> searchAll();
    void remove(@Param("id") Integer id);

    // 같은 유저가 같은 날짜, 같은 내용의 Todo를 추가했는지 확인
    TodoList searchByUserAndDateAndContent(
        @Param("userId") Integer userId, 
        @Param("date") LocalDate date, 
        @Param("content") String content
    );
    
}
