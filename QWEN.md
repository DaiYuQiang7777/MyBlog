# MyBlog 项目说明

这是一个基于Spring Boot的个人博客系统，用于自我学习和实践。项目采用现代化的Java技术栈，支持用户管理、文章发布、评论系统等功能。

## 项目概述

- **项目名称**: MyBlog
- **项目类型**: Java Web应用
- **主要技术栈**: Spring Boot + MyBatis-Plus + MySQL + Redis + Elasticsearch + Vue.js + Spring Security + Flyway + Lombok
- **开发语言**: Java 21
- **构建工具**: Maven
- **项目目标**: 构建一个功能完整的个人博客系统，后续将接入AI功能

## 核心功能模块

1. **用户管理模块**
   - 用户注册/登录/注销
   - 用户信息管理
   - 权限管理（管理员/普通用户）
   - JWT令牌认证

2. **博客文章管理模块**
   - 文章发布、编辑、删除
   - 文章分类和标签管理
   - 文章搜索（基于Elasticsearch）
   - 文章浏览统计

3. **评论系统**
   - 文章评论功能
   - 回复评论
   - 评论审核

4. **系统管理**
   - 使用Flyway进行数据库迁移
   - Redis缓存支持
   - 安全配置（Spring Security）

## 目录结构

```
MyBlog/
├── src/
│   ├── main/
│   │   ├── java/com/myblog/
│   │   │   ├── controller/     # 控制器层
│   │   │   ├── service/        # 业务逻辑层
│   │   │   ├── repository/     # 数据访问层
│   │   │   ├── entity/         # 实体类
│   │   │   ├── config/         # 配置类
│   │   │   └── utils/          # 工具类
│   │   └── resources/
│   │       ├── application.yml # 应用配置
│   │       ├── db/migration/   # Flyway数据库迁移脚本
│   │       └── mapper/         # MyBatis XML映射文件
│   └── test/                   # 测试代码
├── pom.xml                     # Maven依赖配置
└── README.md                   # 项目说明
```

## 重要配置文件

- `pom.xml`: Maven依赖配置，包含Spring Boot、MyBatis-Plus、Redis、Elasticsearch、JWT等依赖
- `application.yml`: 应用配置，包含数据库连接、Redis、Elasticsearch、JWT等配置
- `src/main/resources/db/migration/V1__init_schema.sql`: 数据库初始化脚本

## 数据库表结构

- `users`: 用户表
- `articles`: 文章表
- `categories`: 分类表
- `tags`: 标签表
- `comments`: 评论表
- `article_tags`: 文章标签关联表
- `system_configs`: 系统配置表

## 开发约定

- 使用Lombok简化代码
- 使用MyBatis-Plus进行数据库操作
- 使用Spring Security和JWT进行安全控制
- 使用Flyway进行数据库版本管理
- 使用Redis作为缓存和会话存储
- 使用Elasticsearch提供搜索功能

## 后续计划

- 前端使用Vue.js开发
- 集成AI功能（文章自动生成、智能推荐等）
- 实现全文搜索
- 添加主题和自定义功能
- 增加统计分析功能