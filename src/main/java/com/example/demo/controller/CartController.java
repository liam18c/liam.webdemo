package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Good;
import com.example.demo.mapper.CartMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Array;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {


    @Resource
    CartMapper cartMapper;

    @PostMapping("/add")
    public Result<?> add(@RequestBody Cart cart){
         Cart res = cartMapper.selectOne(Wrappers.<Cart>lambdaQuery().eq(Cart::getUid,cart.getUid()).eq(Cart::getGid,cart.getGid()));
        if(res !=null)
        {
            res.setCount(res.getCount()+cart.getCount());
            cartMapper.updateById(res);
        }
        else {
            cartMapper.insert(cart);
        }
        return Result.success();
    }

    @GetMapping("/{uid}")
    public Result<?> findPage(@PathVariable Long uid) {
        LambdaQueryWrapper<Cart> wrapper=Wrappers.<Cart>lambdaQuery().eq(Cart::getUid,uid);
        Page<Cart> CartPage = cartMapper.selectPage(new Page<>(), wrapper);
        return Result.success(CartPage);
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Cart cart) {
        cartMapper.updateById(cart);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        cartMapper.deleteById(id);
        return Result.success();
    }

//    删除已支付商品
    @PostMapping("/paylist")
    public Result<?> delete(@RequestBody List<Integer> ids) {
        cartMapper.deleteBatchIds(ids);
        return Result.success();
    }
}
