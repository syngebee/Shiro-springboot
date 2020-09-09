package com.cyc.shirospringboot.service.impl;

import com.cyc.shirospringboot.mapper.UserMapper;
import com.cyc.shirospringboot.pojo.User;
import com.cyc.shirospringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getList() {
        return userMapper.getList();
    }

    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }


}
