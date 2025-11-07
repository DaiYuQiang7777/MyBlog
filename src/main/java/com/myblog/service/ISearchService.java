package com.myblog.service;

import com.myblog.entity.Article;
import java.util.List;

public interface ISearchService {
    void indexArticle(Article article);
    void updateArticleIndex(Article article);
    void deleteArticleIndex(Long articleId);
    List<Article> searchArticles(String keyword);
}