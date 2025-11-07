package com.myblog.repository;

import com.myblog.entity.EsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsArticleRepository extends ElasticsearchRepository<EsArticle, Long> {
    Page<EsArticle> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}