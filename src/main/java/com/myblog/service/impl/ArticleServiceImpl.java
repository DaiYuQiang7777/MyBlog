package com.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myblog.entity.Article;
import com.myblog.repository.ArticleRepository;
import com.myblog.service.IArticleService;
import com.myblog.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleRepository, Article> implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private ISearchService searchService;

    @Override
    public Article findById(Long id) {
        String cacheKey = "article:" + id;
        Article article = (Article) redisTemplate.opsForValue().get(cacheKey);
        
        if (article == null) {
            article = articleRepository.selectById(id);
            if (article != null) {
                redisTemplate.opsForValue().set(cacheKey, article, 30, TimeUnit.MINUTES);
            }
        }
        
        return article;
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.selectList(null);
    }

    @Override
    public List<Article> findByAuthorId(Long authorId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id", authorId);
        return articleRepository.selectList(wrapper);
    }

    @Override
    public List<Article> findByStatus(String status) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return articleRepository.selectList(wrapper);
    }

    @Override
    public Article createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        
        if ("PUBLISHED".equals(article.getStatus())) {
            article.setPublishedAt(LocalDateTime.now());
        }
        
        articleRepository.insert(article);
        
        // 添加到搜索索引
        searchService.indexArticle(article);
        
        // 清除相关缓存
        redisTemplate.delete("articles:published");
        
        return article;
    }

    @Override
    public Article updateArticle(Article article) {
        article.setUpdatedAt(LocalDateTime.now());
        
        if ("PUBLISHED".equals(article.getStatus()) && article.getPublishedAt() == null) {
            article.setPublishedAt(LocalDateTime.now());
        }
        
        articleRepository.updateById(article);
        
        // 更新搜索索引
        searchService.updateArticleIndex(article);
        
        // 清除缓存
        String cacheKey = "article:" + article.getId();
        redisTemplate.delete(cacheKey);
        redisTemplate.delete("articles:published");
        
        return article;
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.selectById(id);
        articleRepository.deleteById(id);
        
        // 从搜索索引中删除
        if (article != null) {
            searchService.deleteArticleIndex(id);
        }
        
        // 清除缓存
        String cacheKey = "article:" + id;
        redisTemplate.delete(cacheKey);
        redisTemplate.delete("articles:published");
    }

    @Override
    public void updateViewCount(Long id) {
        Article article = articleRepository.selectById(id);
        if (article != null) {
            article.setViewCount((article.getViewCount() == null ? 0 : article.getViewCount()) + 1);
            articleRepository.updateById(article);
            
            // 更新缓存
            String cacheKey = "article:" + id;
            redisTemplate.delete(cacheKey);
        }
    }

    @Override
    public List<Article> searchArticles(String keyword) {
        // 首先尝试使用Elasticsearch搜索
        List<Article> esResults = searchService.searchArticles(keyword);
        if (!esResults.isEmpty()) {
            return esResults;
        }
        
        // 如果ES搜索没有结果，回退到数据库搜索
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.like("title", keyword).or().like("content", keyword);
        return articleRepository.selectList(wrapper);
    }

    @Override
    public List<Article> findPublishedArticles() {
        String cacheKey = "articles:published";
        List<Article> articles = (List<Article>) redisTemplate.opsForValue().get(cacheKey);
        
        if (articles == null) {
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.eq("status", "PUBLISHED").orderByDesc("published_at");
            articles = articleRepository.selectList(wrapper);
            redisTemplate.opsForValue().set(cacheKey, articles, 10, TimeUnit.MINUTES);
        }
        
        return articles;
    }

    @Override
    public List<Article> findPublishedArticlesByCategory(Long categoryId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId).eq("status", "PUBLISHED").orderByDesc("published_at");
        return articleRepository.selectList(wrapper);
    }
}