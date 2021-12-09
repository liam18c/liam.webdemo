package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Good;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {


    @Resource
    UserMapper userMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
        if(res == null)
        {
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);

    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(res !=null)
        {
            return Result.error("-1","用户已存在");
        }
        else {
            if(user.getAddress()==null){user.setAddress("未设置");}
            if(user.getAge()==null){user.setAge(0);}
            if(user.getSex()==null){user.setSex("未设置");}
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody User user) {
            userMapper.updateById(user);
            return Result.success(userMapper.selectById(user.getId()));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(userMapper.selectById(id));
    }
}
