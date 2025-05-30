package org.itheima.controller;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.itheima.pojo.Result;
import org.itheima.pojo.User;
import org.itheima.service.UserService;
import org.itheima.utils.JwtUtil;
import org.itheima.utils.Md5Util;
import org.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
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
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", u.getId());
                claims.put("username", u.getUsername());
                String token = JwtUtil.genToken(claims);
                //把token存储到redis中
                ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
                ops.set(token,token,12, TimeUnit.HOURS);
                return Result.success(token);
            }else{
                return Result.error("密码错误");
            }
        }
    }

    @RequestMapping("userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        try {
//            Map<String, Object> claims = JwtUtil.parseToken(token);
//            String username = (String) claims.get("username");
            Map<String, Object> claims = ThreadLocalUtil.get();
            String username = (String) claims.get("username");
            User u = userService.findByUsername(username);
            return Result.success(u);
        } catch (Exception e) {
            return Result.error("未登录");
        }
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader(name = "Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要参数");
        }
        Map<String,Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User u = userService.findByUsername(username);
        if(!u.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }
        if(!newPwd.equals(rePwd)){
            return Result.error("两次填写密码不一致");
        }
        userService.updatePwd(newPwd);
        //删除redis中的token
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.getOperations().delete(token);
        return Result.success();
    }
}
