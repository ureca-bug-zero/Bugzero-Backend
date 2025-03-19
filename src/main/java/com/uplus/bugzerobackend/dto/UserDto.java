package com.uplus.bugzerobackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
	private Integer id; //user 
	private Integer weekScore = 0;
	private Integer rank = 0;
}
