package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("good")
@Data
public class Good {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String goodname;
    private BigDecimal price;
    private String photo;
    private String address;
}
