package com.myblog.service.impl;

import com.myblog.entity.Article;
import com.myblog.entity.EsArticle;
import com.myblog.repository.EsArticleRepository;
import com.myblog.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private EsArticleRepository esArticleRepository;

    @Override
    public void indexArticle(Article article) {
        EsArticle esArticle = convertToEsArticle(article);
        esArticleRepository.save(esArticle);
    }

    @Override
    public void updateArticleIndex(Article article) {
        indexArticle(article); // 更新和索引使用相同操作
    }

    @Override
    public void deleteArticleIndex(Long articleId) {
        esArticleRepository.deleteById(articleId);
    }

    @Override
    public List<Article> searchArticles(String keyword) {
        Pageable pageable = PageRequest.of(0, 10); // 默认返回前10条结果
        org.springframework.data.domain.Page<EsArticle> esArticles = 
            esArticleRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        
        // 将ES实体转换回数据库实体
        // 注意：在实际实现中，你可能需要从数据库获取完整信息，或在ES中存储完整信息
        return esArticles.getContent().stream().map(this::convertToArticle).collect(Collectors.toList());
    }
    
    private EsArticle convertToEsArticle(Article article) {
        EsArticle esArticle = new EsArticle();
        esArticle.setId(article.getId());
        esArticle.setTitle(article.getTitle());
        esArticle.setContent(article.getContent());
        esArticle.setSummary(article.getSummary());
        esArticle.setAuthorId(article.getAuthorId());
        esArticle.setCategoryId(article.getCategoryId());
        esArticle.setStatus(article.getStatus());
        esArticle.setViewCount(article.getViewCount());
        esArticle.setLikesCount(article.getLikesCount());
        esArticle.setCommentCount(article.getCommentCount());
        
        return esArticle;
    }
    
    private Article convertToArticle(EsArticle esArticle) {
        // 由于ES中的数据可能不完整，这里实际应用中可能需要从数据库获取完整信息
        // 为了简化，我们只映射基本字段
        Article article = new Article();
        article.setId(esArticle.getId());
        article.setTitle(esArticle.getTitle());
        article.setContent(esArticle.getContent());
        article.setSummary(esArticle.getSummary());
        article.setAuthorId(esArticle.getAuthorId());
        article.setCategoryId(esArticle.getCategoryId());
        article.setStatus(esArticle.getStatus());
        article.setViewCount(esArticle.getViewCount());
        article.setLikesCount(esArticle.getLikesCount());
        article.setCommentCount(esArticle.getCommentCount());
        
        return article;
    }
}