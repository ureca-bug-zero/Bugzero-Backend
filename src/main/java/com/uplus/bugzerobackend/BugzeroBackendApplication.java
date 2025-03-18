package com.uplus.bugzerobackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.uplus.bugzerobackend.mapper")
@EnableScheduling //Spring이 scheduling 기능을 사용할 수 있게함.
@SpringBootApplication 
public class BugzeroBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugzeroBackendApplication.class, args);
	}

}
