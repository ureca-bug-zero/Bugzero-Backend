package com.uplus.bugzerobackend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uplus.bugzerobackend.domain.Friend;
import com.uplus.bugzerobackend.domain.User;
import com.uplus.bugzerobackend.dto.FriendListDto;
import com.uplus.bugzerobackend.mapper.FriendMapper;
import com.uplus.bugzerobackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                response.add(new FriendListDto(friendId, user.getName()));
            }
        }
        return response;
    }
}
