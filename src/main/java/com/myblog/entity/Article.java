package com.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("articles")
public class Article {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String title;
    private String slug;
    private String content;
    private String summary;
    private String coverImage;
    private Long authorId;
    private Long categoryId;
    private String status; // DRAFT, PUBLISHED, ARCHIVED
    private Integer viewCount;
    private Integer likesCount;
    private Integer commentCount;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}