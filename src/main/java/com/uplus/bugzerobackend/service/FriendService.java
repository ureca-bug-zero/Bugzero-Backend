package com.uplus.bugzerobackend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uplus.bugzerobackend.domain.Friend;
import com.uplus.bugzerobackend.domain.User;
import com.uplus.bugzerobackend.dto.FriendListDto;
import com.uplus.bugzerobackend.mapper.FriendMapper;
import com.uplus.bugzerobackend.mapper.UserMapper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendService {
    private final FriendMapper friendMapper;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public FriendService(FriendMapper friendMapper, UserMapper userMapper) {
        this.friendMapper = friendMapper;
        this.userMapper = userMapper;
    }
    
    public List<FriendListDto> getFriend(Integer userId) {
        Friend friend = friendMapper.getFriendById(userId);
        if (friend == null || friend.getFriendList() == null) {
            return new ArrayList<>();
        }
        List<Integer> friendIds = friend.getFriendList();
        List<FriendListDto> response = new ArrayList<>();
        for(Integer friendId : friendIds) {
            User user = userMapper.getUserById(friendId);
            if(user!=null) {
            	response.add(new FriendListDto(friendId, user.getName(), user.getEmail()));
            }
        }
        return response;
    }
	public void deleteFriend(int userId, int friendId) {
		Map<String, Object> rParams = new HashMap<>();
		rParams.put("userId", userId);
		rParams.put("friendId", friendId);
		
		Map<String, Object> sParams = new HashMap<>();
		sParams.put("userId", friendId);
		sParams.put("friendId", userId);
		
		if(friendMapper.checkFriendExistence(rParams) == 0) {
			throw new EntityNotFoundException("존재하지 않는 친구입니다.");
		}
	   
	    friendMapper.deleteFriend(rParams);
	    friendMapper.deleteFriend(sParams);
	}
}