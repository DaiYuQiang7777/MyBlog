# MyBlog 部署指南

## 环境要求

- Java 21
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Elasticsearch 7.0+ (可选，用于搜索功能)

## 部署步骤

### 1. 克隆项目

```bash
git clone https://github.com/yourusername/MyBlog.git
cd MyBlog
```

### 2. 配置数据库

创建数据库：

```sql
CREATE DATABASE myblog CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 修改配置文件

编辑 `src/main/resources/application.yml` 文件，更新数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/myblog?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_db_username
    password: your_db_password
```

### 4. 编译项目

```bash
mvn clean package -DskipTests
```

### 5. 运行应用

```bash
java -jar target/myblog-1.0.0-SNAPSHOT.jar
```

### 6. Docker 部署 (可选)

创建 `Dockerfile`:

```dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/myblog-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

构建并运行 Docker 容器：

```bash
docker build -t myblog .
docker run -p 8080:8080 -d myblog
```

## API 接口

### 用户相关
- `POST /api/users/register` - 用户注册
- `POST /api/users/login` - 用户登录
- `GET /api/users/{id}` - 获取用户信息

### 文章相关
- `GET /api/articles` - 获取所有文章
- `GET /api/articles/published` - 获取已发布文章
- `GET /api/articles/{id}` - 获取文章详情
- `POST /api/articles` - 创建文章
- `PUT /api/articles/{id}` - 更新文章
- `DELETE /api/articles/{id}` - 删除文章
- `GET /api/articles/search?keyword={keyword}` - 搜索文章

### 评论相关
- `GET /api/comments/article/{articleId}` - 获取文章评论
- `POST /api/comments` - 发表评论
- `PUT /api/comments/{id}/approve` - 批准评论
- `PUT /api/comments/{id}/reject` - 拒绝评论

## 前端页面

- `/` - 首页
- `/login` - 登录页
- `/register` - 注册页
- `/profile` - 个人中心
- `/create-article` - 发布文章
- `/article/{id}` - 文章详情页

## 后续计划

1. 集成AI功能：
   - 文章自动生成
   - 智能摘要生成
   - 内容推荐系统

2. 性能优化：
   - 数据库查询优化
   - 缓存策略优化
   - 静态资源优化

3. 功能扩展：
   - 文件上传功能
   - 评论审核系统
   - 文章标签系统
   - 阅读统计分析