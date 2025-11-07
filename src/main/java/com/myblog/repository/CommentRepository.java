package com.myblog.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myblog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepository extends BaseMapper<Comment> {
}