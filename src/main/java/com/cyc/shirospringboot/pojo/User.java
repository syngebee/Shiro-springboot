package com.cyc.shirospringboot.pojo;

import lombok.Data;

@Data
public class User {
    private Integer uid;
    private String username;
    private String password;
    private String perms;
}
