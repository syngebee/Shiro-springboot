package com.cyc.shirospringboot.service;

import com.cyc.shirospringboot.pojo.User;

import java.util.List;

public interface UserService {
    List<User> getList();

    User getUserByUserName(String username);
}
