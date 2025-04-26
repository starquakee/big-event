package org.itheima.service.impl;

import org.itheima.mapper.UserMapper;
import org.itheima.pojo.User;
import org.itheima.service.UserService;
import org.itheima.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 加密
        String md5Pwd = Md5Util.getMD5String(password);
        // 添加用户
        userMapper.add(username,md5Pwd);

    }
}
