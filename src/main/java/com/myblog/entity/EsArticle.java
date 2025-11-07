package com.myblog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.Data;

@Data
@Document(indexName = "articles")
public class EsArticle {

    @Id
    private Long id;
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String summary;
    
    @Field(type = FieldType.Keyword)
    private Long authorId;
    
    @Field(type = FieldType.Long)
    private Long categoryId;
    
    @Field(type = FieldType.Keyword)
    private String status;
    
    @Field(type = FieldType.Integer)
    private Integer viewCount;
    
    @Field(type = FieldType.Integer)
    private Integer likesCount;
    
    @Field(type = FieldType.Integer)
    private Integer commentCount;
}