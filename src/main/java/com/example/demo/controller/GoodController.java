package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Good;
import com.example.demo.mapper.GoodMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.print.Book;

@RestController
@CrossOrigin
@RequestMapping("/good")
public class GoodController{

    @Resource
    GoodMapper goodMapper;

//    新增数据
    @PostMapping
    public Result<?> save(@RequestBody Good good) {
        goodMapper.insert(good);
        return Result.success();
    }

//    更新数据
    @PutMapping
    public Result<?> update(@RequestBody Good good) {
        goodMapper.updateById(good);
        return Result.success();
    }

//    查询数据
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Good> wrapper=Wrappers.<Good>lambdaQuery();
        if(StrUtil.isNotBlank(search))
        {
           wrapper.like(Good::getGoodname, search);
        }
        Page<Good> GoodPage = goodMapper.selectPage(new Page<>(), wrapper);
        return Result.success(GoodPage);
    }

//    删除数据
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
    goodMapper.deleteById(id);
    return Result.success();
    }


}
