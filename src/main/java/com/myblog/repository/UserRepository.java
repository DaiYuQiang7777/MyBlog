package com.myblog.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myblog.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository extends BaseMapper<User> {
}