package com.uplus.bugzerobackend.service;

import lombok.RequiredArgsConstructor; //final 필드에서 자동으로 생성자 만듦
import lombok.extern.slf4j.Slf4j; //log

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import com.uplus.bugzerobackend.controller.TodoListController;
import com.uplus.bugzerobackend.domain.TodoList;
import com.uplus.bugzerobackend.dto.TodoListDto;
import com.uplus.bugzerobackend.dto.UserDto;
import com.uplus.bugzerobackend.mapper.TodoListMapper;
import com.uplus.bugzerobackend.mapper.UserMapper;

@Slf4j
@Service
@RequiredArgsConstructor
// SchedulingConfigurer: 스케줄링 설정을 프로그래밍 방식으로 제어할 수 있는 인터페이스
public class SchedulerService implements SchedulingConfigurer{
	@Value("${schedule.missionCron}") // application.properties에 값 지정
	private String missionCronExpression;
	
	@Value("${schedule.missionUse}")
	private boolean isMissionSchedulerEnabled;
	
	@Value("${schedule.rankingCron}")
	private String rankingCronExpression;
	
	@Value("${schedule.rankingUse}")
	private boolean isRankingSchedulerEnabled;
	
	private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler(); // 실제 작업 실행할 스레드 풀 (기본 1개의 스레드) 
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// TODO Auto-generated method stub
		taskScheduler.initialize(); // 스케줄러 초기화 
		// 미션 자동화
		if(isMissionSchedulerEnabled) {
			taskRegistrar.addTriggerTask(
					this::runMissionScheduledTask, //실행할 작업 _ cronExpression에 정의된 간격으로 반복 실행
					this.getMissionTrigger() //트리거 설정
					);
		}
		// 랭킹 초기화
		if(isRankingSchedulerEnabled) {
			taskRegistrar.addTriggerTask(
					this::runRankingScheduledTask, //실행할 작업 _ cronExpression에 정의된 간격으로 반복 실행
					this.getRankingTrigger() //트리거 설정
					);
		}
	}
	
	private final UserMapper userMapper;
	private final TodoListMapper todoListMapper;
	
	private void runMissionScheduledTask() {
		List<UserDto> userList = userMapper.findAll();
		Random random = new Random();
		int randomNum = random.nextInt(45000 - 1000) + 1000 ; // 1000 - 44999 사이 랜덤 숫자
		
		for (UserDto user : userList) {
			// weekScore 갱신 _ search(todo) 수정 후 다시
			int originWeekScore = user.getWeekScore();
			List<TodoListDto> searchedTodoList = todoListMapper.searchAll(user.getId(), LocalDate.now().minusDays(1));
			// 체크한 내용 크기 
			long checkedCount = searchedTodoList.stream().filter(todo -> todo.isChecked()).count();
			// 전체 크기
			int totalCount = searchedTodoList.size();
			// 미션인 값 가져와서 isChecked인지 확인
			Optional<TodoListDto> missionTodo = searchedTodoList.stream().filter(todo -> todo.isMission()).findFirst();
			boolean isMissionChecked = missionTodo.map(TodoListDto::isChecked).orElse(false);
			
			if(isMissionChecked) {
				double newScore =  originWeekScore + ((double) checkedCount/totalCount * 1.5);
				log.info("{}" , newScore);
				user.setWeekScore((int) Math.round(newScore));
			} else {
				double newScore = originWeekScore + ((double) checkedCount/totalCount);
				log.info("{}" , newScore);
				user.setWeekScore((int) Math.round(newScore));
			}
			
			userMapper.update(user);
			
			// 미션 자동화 
			TodoListDto todo = new TodoListDto();
			todo.setUserId(user.getId());
			todo.setContent("오늘의 미션 문제");
			todo.setLink("https://www.acmicpc.net/problem/" + randomNum);
			todo.setChecked(false);
			todo.setMission(true);
			todo.setDate(LocalDate.now());
			
			todoListMapper.insert(todo);
			
		}
		log.info("현재 시간: {}", System.currentTimeMillis());
	}
	private Trigger getMissionTrigger() {
		return new org.springframework.scheduling.support.CronTrigger(missionCronExpression);
	}
	
	// 랭킹 초기화
	private void runRankingScheduledTask() {
		List<UserDto> userList = userMapper.findAll();

		userList.sort((u1, u2) -> u2.getWeekScore().compareTo(u1.getWeekScore()));// weekScore 기준 내림차순
	    
	    int rank = 1; // 현재 랭킹
	    int sameRankCount = 1; // 같은 점수인 사용자 수

	    for (int i = 0; i < userList.size(); i++) {
	        UserDto user = userList.get(i);

	        // 이전 사용자와 점수가 다르면 랭킹 증가
	        if (i > 0 && !user.getWeekScore().equals(userList.get(i - 1).getWeekScore())) {
	            rank += sameRankCount;
	            sameRankCount = 1; // 동일 점수 개수 초기화
	        } else if (i > 0) {
	            sameRankCount++;
	        }

	        user.setRank(rank);
	        userMapper.update(user);
	    }
	    
	    for (int i = 0; i < userList.size(); i++) {
	    	UserDto user = userList.get(i);
	    	user.setWeekScore(0);
	    	userMapper.update(user);
	    }

		log.info("현재 시간: {}", System.currentTimeMillis());
	}
	private Trigger getRankingTrigger() {
		return new org.springframework.scheduling.support.CronTrigger(rankingCronExpression);

	}
}