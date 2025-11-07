-- 初始化用户表
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    email VARCHAR(100) UNIQUE NOT NULL COMMENT '邮箱',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    bio TEXT COMMENT '个人简介',
    role VARCHAR(20) DEFAULT 'USER' COMMENT '用户角色',
    status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '用户状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 初始化分类表
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL COMMENT '分类名称',
    description TEXT COMMENT '分类描述',
    parent_id BIGINT DEFAULT NULL COMMENT '父分类ID',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES categories(id)
);

-- 初始化标签表
CREATE TABLE tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL COMMENT '标签名称',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 初始化文章表
CREATE TABLE articles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL COMMENT '文章标题',
    slug VARCHAR(255) UNIQUE COMMENT 'URL友好标识',
    content LONGTEXT NOT NULL COMMENT '文章内容',
    summary TEXT COMMENT '文章摘要',
    cover_image VARCHAR(255) COMMENT '封面图片',
    author_id BIGINT NOT NULL COMMENT '作者ID',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    status VARCHAR(20) DEFAULT 'DRAFT' COMMENT '文章状态',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    likes_count INT DEFAULT 0 COMMENT '点赞数',
    comment_count INT DEFAULT 0 COMMENT '评论数',
    published_at TIMESTAMP NULL COMMENT '发布时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- 初始化文章标签关联表
CREATE TABLE article_tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    article_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
    UNIQUE KEY unique_article_tag (article_id, tag_id)
);

-- 初始化评论表
CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    article_id BIGINT NOT NULL COMMENT '文章ID',
    parent_id BIGINT NULL COMMENT '父评论ID',
    author_id BIGINT NOT NULL COMMENT '评论者ID',
    content TEXT NOT NULL COMMENT '评论内容',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT '评论状态',
    likes_count INT DEFAULT 0 COMMENT '点赞数',
    ip_address VARCHAR(45) COMMENT 'IP地址',
    user_agent TEXT COMMENT '用户代理',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE CASCADE
);

-- 初始化系统配置表
CREATE TABLE system_configs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key VARCHAR(100) UNIQUE NOT NULL COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    description VARCHAR(255) COMMENT '配置描述',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 插入默认管理员用户 (密码需要先加密)
-- INSERT INTO users (username, email, password, nickname, role, status) VALUES ('admin', 'admin@example.com', '$2a$10$seZLu34Q6L6F7yDnHqLz2u3vqF5w8yP7xJ9zR4mK1oL6pN0cA2vQ6', 'Admin', 'ADMIN', 'ACTIVE');