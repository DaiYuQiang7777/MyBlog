# MyBlog

一个基于Spring Boot的个人博客系统，用于自我学习和实践。

## 技术栈

- **后端**: Spring Boot 3.2+, Java 21
- **持久层**: MyBatis-Plus
- **数据库**: MySQL
- **缓存**: Redis
- **搜索引擎**: Elasticsearch
- **安全**: Spring Security + JWT
- **数据库迁移**: Flyway
- **前端**: Vue.js, Bootstrap
- **构建工具**: Maven
- **其他**: Lombok, Apache Commons

## 功能特性

- 用户注册/登录/权限管理 (JWT认证)
- 博客文章发布/编辑/管理
- 文章分类和标签系统
- 评论系统 (支持审核)
- 搜索功能（基于Elasticsearch）
- 响应式前端界面
- RESTful API
- 数据缓存 (Redis)
- 数据库迁移 (Flyway)

## 项目结构

```
MyBlog/
├── src/
│   ├── main/
│   │   ├── java/com/myblog/
│   │   │   ├── controller/     # 控制器层
│   │   │   ├── service/        # 业务逻辑层
│   │   │   ├── service/impl/   # 业务逻辑实现
│   │   │   ├── repository/     # 数据访问层
│   │   │   ├── entity/         # 实体类
│   │   │   ├── config/         # 配置类
│   │   │   └── utils/          # 工具类
│   │   └── resources/
│   │       ├── application.yml # 应用配置
│   │       ├── db/migration/   # Flyway数据库迁移脚本
│   │       ├── mapper/         # MyBatis XML映射文件
│   │       └── static/         # 静态资源 (前端页面)
│   └── test/                   # 测试代码
├── pom.xml                     # Maven依赖配置
├── README.md                   # 项目说明
└── DEPLOYMENT.md               # 部署指南
```

## 快速开始

### 环境要求
- Java 21
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+ (可选)
- Elasticsearch 7.0+ (可选)

### 开始使用

1. 克隆项目：
   ```bash
   git clone https://github.com/yourusername/MyBlog.git
   cd MyBlog
   ```

2. 创建数据库：
   ```sql
   CREATE DATABASE myblog CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. 修改数据库配置：
   在 `src/main/resources/application.yml` 中修改数据库连接信息

4. 启动应用：
   ```bash
   mvn spring-boot:run
   ```

5. 访问应用：
   打开浏览器访问 `http://localhost:8080`

## API 接口

### 用户相关
- `POST /api/users/register` - 用户注册
- `POST /api/users/login` - 用户登录
- `GET /api/users/{id}` - 获取用户信息
- `PUT /api/users/{id}` - 更新用户信息
- `DELETE /api/users/{id}` - 删除用户

### 文章相关
- `GET /api/articles` - 获取所有文章
- `GET /api/articles/published` - 获取已发布文章
- `GET /api/articles/{id}` - 获取文章详情
- `POST /api/articles` - 创建文章
- `PUT /api/articles/{id}` - 更新文章
- `DELETE /api/articles/{id}` - 删除文章
- `GET /api/articles/search?keyword={keyword}` - 搜索文章
- `GET /api/articles/author/{authorId}` - 获取用户的文章
- `GET /api/articles/category/{categoryId}` - 获取分类下的文章

### 评论相关
- `GET /api/comments/article/{articleId}` - 获取文章评论
- `POST /api/comments` - 发表评论
- `PUT /api/comments/{id}/approve` - 批准评论
- `PUT /api/comments/{id}/reject` - 拒绝评论
- `DELETE /api/comments/{id}` - 删除评论

## 前端页面

- `/` - 首页
- `/login` - 登录页
- `/register` - 注册页
- `/profile` - 个人中心
- `/profile/articles` - 我的文章列表
- `/create-article` - 发布文章
- `/article/{id}` - 文章详情页

## 后续计划

1. 集成AI功能：
   - 文章自动生成
   - 智能摘要生成
   - 内容推荐系统
   - 智能标签推荐

2. 功能扩展：
   - 文件上传功能（图片/附件）
   - 文章标签系统
   - 阅读统计分析
   - 主题自定义
   - RSS订阅

3. 性能优化：
   - 数据库查询优化
   - 缓存策略优化
   - 静态资源优化
   - Elasticsearch全文搜索

4. 安全增强：
   - 验证码功能
   - 访问频率限制
   - 输入数据验证

## 部署

详见 `DEPLOYMENT.md` 文件。