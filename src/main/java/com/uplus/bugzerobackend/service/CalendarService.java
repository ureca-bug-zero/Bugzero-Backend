package com.uplus.bugzerobackend.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.uplus.bugzerobackend.dto.CalendarRequestDto;
import com.uplus.bugzerobackend.dto.TodoListDto;

@Service
public class CalendarService {

    // 공통 진행률 계산 함수
    public Map<Integer, Double> processProgress(List<TodoListDto> todoLists) {
        Map<Integer, Double> scoreMap = new HashMap<>();
        Map<Integer, Double> totalScoreMap = new HashMap<>();

        for (TodoListDto todo : todoLists) {
            int day = todo.getDate().getDayOfMonth();
            double weight = todo.isMission() ? 1.5 : 1.0;
            double score = todo.isChecked() ? weight : 0.0;

            scoreMap.put(day, scoreMap.getOrDefault(day, 0.0) + score);
            totalScoreMap.put(day, totalScoreMap.getOrDefault(day, 0.0) + weight);
        }

        Map<Integer, Double> progressMap = new HashMap<>();
        for (int day : totalScoreMap.keySet()) {
            double total = totalScoreMap.get(day);
            double progress = (total > 0) ? (scoreMap.getOrDefault(day, 0.0) / total) * 100 : 0.0;
            progress = Math.round(progress * 100.0) / 100.0;
            progressMap.put(day, progress);
        }

        return progressMap;
    }

    // 년-월별 투두 진행률 계산
    public Map<String, Object> processMonthly(List<TodoListDto> todoLists, CalendarRequestDto request) {
        System.out.println("=== [디버깅] 요청 정보 ===");
        System.out.println("yearMonth: " + request.getYearMonth());

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


    // 특정 날짜 투두 진행률 계산
//    public Map<String, Object> processDaily(List<TodoListDto> todoLists, CalendarRequestDto request) {
//        System.out.println("=== [디버깅] 요청 정보 ===");
//        System.out.println("date: " + request.getDate());
//
//        Map<Integer, Double> progressMap = processProgress(todoLists);
//        int day = request.getDate().getDayOfMonth();
//        double score = progressMap.getOrDefault(day, 0.0);
//        score = Math.round(score * 100.0) / 100.0;
//
//        System.out.println("\n=== [디버깅] 특정 날짜 진행률 ===");
//        System.out.println("progressMap: " + progressMap);
//        System.out.println("score: " + score);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("date", request.getDate());
//        response.put("score", score);
//
//        return response;
//    }
}
