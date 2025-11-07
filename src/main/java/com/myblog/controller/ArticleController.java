package com.myblog.controller;

import com.myblog.entity.Article;
import com.myblog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Article article = articleService.findById(id);
        if (article != null) {
            // 更新浏览次数
            articleService.updateViewCount(id);
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/published")
    public ResponseEntity<List<Article>> getPublishedArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // 这里可以添加分页功能
        return ResponseEntity.ok(articleService.findPublishedArticles());
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article createdArticle = articleService.createArticle(article);
        return ResponseEntity.ok(createdArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        article.setId(id);
        Article updatedArticle = articleService.updateArticle(article);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok().body("文章删除成功");
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Article>> getArticlesByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(articleService.findByAuthorId(authorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Article>> getArticlesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(articleService.findByStatus(status));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Article>> searchArticles(@RequestParam String keyword) {
        return ResponseEntity.ok(articleService.searchArticles(keyword));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Article>> getArticlesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(articleService.findPublishedArticlesByCategory(categoryId));
    }
}