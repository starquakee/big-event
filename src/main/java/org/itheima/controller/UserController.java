package org.itheima.controller;

import jakarta.validation.constraints.Pattern;
import org.itheima.pojo.Result;
import org.itheima.pojo.User;
import org.itheima.service.UserService;
import org.itheima.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password) {
        User u = userService.findByUsername(username);
        if(u==null){
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password) {
        User u = userService.findByUsername(username);
        if(u==null){
            return Result.error("用户名错误");
        }else{
            if(u.getPassword().equals(Md5Util.getMD5String(password))){
                return Result.success("jwt token");
            }else{
                return Result.error("密码错误");
            }
        }
    }
}
