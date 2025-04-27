package org.itheima.service.impl;

import org.itheima.mapper.UserMapper;
import org.itheima.pojo.User;
import org.itheima.service.UserService;
import org.itheima.utils.Md5Util;
import org.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

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
    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }
    @Override
    public void updateAvatar(String avatar) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updateAvatar(avatar, id);
    }
    @Override
    public void updatePwd(String password) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        String md5Pwd = Md5Util.getMD5String(password);
        userMapper.updatePwd(md5Pwd,id);
    }
}
