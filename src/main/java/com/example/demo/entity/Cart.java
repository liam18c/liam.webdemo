package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("cart")
@Data
public class Cart {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private Integer gid;
    private Integer count;
    private BigDecimal price;
    private String goodname;
    private String photo;
    private String address;
    private Integer status;
}
