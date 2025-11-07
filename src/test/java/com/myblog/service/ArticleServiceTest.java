package com.myblog.service;

import com.myblog.entity.Article;
import com.myblog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ArticleServiceTest {

    @Autowired
    private IArticleService articleService;
    
    @Autowired
    private IUserService userService;

    @Test
    void testCreateAndFindArticle() {
        // 先创建一个用户
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setNickname("Test User");
        
        User savedUser = userService.registerUser(user);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        
        // 创建文章
        Article article = new Article();
        article.setTitle("Test Article");
        article.setContent("This is a test article content.");
        article.setAuthorId(savedUser.getId());
        article.setCategoryId(1L);
        article.setStatus("PUBLISHED");
        
        Article savedArticle = articleService.createArticle(article);
        
        assertNotNull(savedArticle);
        assertNotNull(savedArticle.getId());
        assertEquals("Test Article", savedArticle.getTitle());
        
        // 查找文章
        Article foundArticle = articleService.findById(savedArticle.getId());
        assertNotNull(foundArticle);
        assertEquals(savedArticle.getId(), foundArticle.getId());
        
        // 清理测试数据
        articleService.deleteArticle(savedArticle.getId());
        userService.deleteUser(savedUser.getId());
    }
}