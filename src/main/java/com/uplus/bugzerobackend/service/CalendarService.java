package com.uplus.bugzerobackend.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.uplus.bugzerobackend.dto.CalendarRequestDto;
import com.uplus.bugzerobackend.dto.TodoListDto;

@Service
public class CalendarService {
    public Map<Integer, Double> processProgress(List<TodoListDto> todoLists) {
        Map<Integer, Double> progressMap = new HashMap<>();
        // 체크한 내용 크기
        long checkedCount = todoLists.stream().filter(todo -> todo.isChecked()).count();
        // 전체 크기
        int totalCount = todoLists.size();
        // 미션인 값 가져와서 isChecked인지 확인
        Optional<TodoListDto> missionTodo = todoLists.stream().filter(todo -> todo.isMission()).findFirst();
        boolean isMissionChecked = missionTodo.map(TodoListDto::isChecked).orElse(false);

        double score = 0.0;
        if (totalCount > 0) {
            score = (double) checkedCount / totalCount * (isMissionChecked ? 1.5 : 1.0);
        }
        double progress = Math.round(score * 100.0); // 소수점 2자리 반올림
        for (TodoListDto todo : todoLists) {
            int day = todo.getDate().getDayOfMonth();
            progressMap.put(day, progress);
        }
        return progressMap;
    }
    // 년-월별 투두 진행률 계산
    public Map<String, Object> processMonthly(List<TodoListDto> todoLists, String yearMonth) {
        System.out.println("=== [디버깅] 요청 정보 ===");
        System.out.println("yearMonth: " + yearMonth);
        Map<Integer, Double> progressMap = processProgress(todoLists);
        // 1일부터 31일까지 기본값 0.0으로 초기화
        Map<Integer, Double> fullProgressMap = new HashMap<>();
        for (int i = 1; i <= 31; i++) {
            fullProgressMap.put(i, 0.0);
        }
        // 기존 진행률 데이터를 fullProgressMap에 반영
        for (Map.Entry<Integer, Double> entry : progressMap.entrySet()) {
            fullProgressMap.put(entry.getKey(), entry.getValue());
        }
        System.out.println("\n=== [디버깅] 최종 진행률 ===");
        System.out.println("progressMap: " + fullProgressMap);
        Map<String, Object> response = new HashMap<>();
        response.put("score", fullProgressMap);
        return response;
    }
}