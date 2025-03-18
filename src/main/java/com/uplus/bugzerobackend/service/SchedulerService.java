package com.uplus.bugzerobackend.service;

import lombok.RequiredArgsConstructor; //final 필드에서 자동으로 생성자 만듦

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// SchedulingConfigurer: 스케줄링 설정을 프로그래밍 방식으로 제어할 수 있는 인터페이스
public class SchedulerConfiguration implements SchedulingConfigurer{
	@Value("${schedule.cron}") // application.properties에 값 지정
	private String cronExpression;
	
	@Value("${schedule.use}")
	private boolean isSchedulerEnabled;
	
	private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler(); // 실제 작업 실행할 스레드 풀 (기본 1개의 스레드)
	
	private static final Logger log = LoggerFactory.getLogger(SchedulerConfiguration.class);
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// TODO Auto-generated method stub
		taskScheduler.initialize(); // 스케줄러 초기화 
		if(isSchedulerEnabled) {
			taskRegistrar.addTriggerTask(
					this::runScheduledTask, //실행할 작업 _ cronExpression에 정의된 간격으로 반복 실행
					this.getTrigger() //트리고 설정
					);
		}
	}
	
	private void runScheduledTask() {
		log.info("현재 시간: {}", System.currentTimeMillis());
	}
	
	private Trigger getTrigger() {
		return new org.springframework.scheduling.support.CronTrigger(cronExpression);
		
	}
}