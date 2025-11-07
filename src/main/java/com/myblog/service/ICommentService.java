package com.myblog.service;

import com.myblog.entity.Comment;
import java.util.List;

public interface ICommentService {
    Comment findById(Long id);
    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByArticleIdAndStatus(Long articleId, String status);
    List<Comment> findByParentId(Long parentId);
    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    void deleteComment(Long id);
    List<Comment> findAll();
    List<Comment> findByStatus(String status);
    void approveComment(Long id);
    void rejectComment(Long id);
}