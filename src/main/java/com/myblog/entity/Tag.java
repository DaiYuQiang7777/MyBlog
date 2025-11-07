package com.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tags")
public class Tag {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String name;
    private LocalDateTime createdAt;
}