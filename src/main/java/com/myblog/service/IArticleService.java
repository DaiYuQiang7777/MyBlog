package com.myblog.service;

import com.myblog.entity.Article;
import java.util.List;

public interface IArticleService {
    Article findById(Long id);
    List<Article> findAll();
    List<Article> findByAuthorId(Long authorId);
    List<Article> findByStatus(String status);
    Article createArticle(Article article);
    Article updateArticle(Article article);
    void deleteArticle(Long id);
    void updateViewCount(Long id);
    List<Article> searchArticles(String keyword);
    List<Article> findPublishedArticles();
    List<Article> findPublishedArticlesByCategory(Long categoryId);
}