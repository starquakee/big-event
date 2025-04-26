package org.itheima.service;

import org.itheima.pojo.User;

public interface UserService {
    // 根据用户名查询用户
    User findByUsername(String username);

    // 用户注册
    void register(String username, String password);
}
