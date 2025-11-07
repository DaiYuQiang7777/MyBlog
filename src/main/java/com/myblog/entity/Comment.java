package com.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("comments")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long articleId;
    private Long parentId;
    private Long authorId;
    private String content;
    private String status; // PENDING, APPROVED, REJECTED
    private Integer likesCount;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}