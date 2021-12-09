package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Record;
import com.example.demo.mapper.CartMapper;
import com.example.demo.mapper.RecordMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/record")
public class RecordController {


    @Resource
    RecordMapper recordMapper;

    @PostMapping("/add")
    public Result<?> add(@RequestBody List<Record> records){
        for (int i=0;i<records.size();i++)
        {
            records.get(i).setRecordstatus("等待发货");
            recordMapper.insert(records.get(i));
        }
        return Result.success();
    }

    @GetMapping("/{uid}")
    public Result<?> findPage(@PathVariable Long uid) {
        LambdaQueryWrapper<Record> wrapper = Wrappers.<Record>lambdaQuery().eq(Record::getUid, uid);
        Page<Record> RecordPage = recordMapper.selectPage(new Page<>(), wrapper);
        return Result.success(RecordPage);
    }

    @GetMapping
    public Result<?> findPage(){
        LambdaQueryWrapper<Record> wrapper = Wrappers.<Record>lambdaQuery();
        Page<Record> RecordPage = recordMapper.selectPage(new Page<>(), wrapper);
        return Result.success(RecordPage);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable long id) {
        Record record = recordMapper.selectById(id);
        record.setRecordstatus("已发货");
        recordMapper.updateById(record);
        return Result.success();
    }
}
