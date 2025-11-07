package com.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String avatar;
    private String bio;
    private String role; // ADMIN, USER
    private String status; // ACTIVE, INACTIVE, BANNED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}