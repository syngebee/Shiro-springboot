package com.cyc.shirospringboot.mapper;

import com.cyc.shirospringboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> getList();

    @Select("select * from user where username=#{username}")
    User getUserByUserName(String username);
}
