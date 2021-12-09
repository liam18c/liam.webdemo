package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("record")
@Data
public class Record {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private Integer gid;
    private String goodname;
    private BigDecimal price;
    private Integer count;
    private String photo;
    private String uaddress;
    private String gaddress;
    private String recordstatus;

}
