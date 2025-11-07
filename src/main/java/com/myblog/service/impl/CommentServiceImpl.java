package com.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myblog.entity.Comment;
import com.myblog.repository.CommentRepository;
import com.myblog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentRepository, Comment> implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        return commentRepository.selectById(id);
    }

    @Override
    public List<Comment> findByArticleId(Long articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId).orderByDesc("created_at");
        return commentRepository.selectList(wrapper);
    }

    @Override
    public List<Comment> findByArticleIdAndStatus(Long articleId, String status) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId).eq("status", status).orderByDesc("created_at");
        return commentRepository.selectList(wrapper);
    }

    @Override
    public List<Comment> findByParentId(Long parentId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        return commentRepository.selectList(wrapper);
    }

    @Override
    public Comment createComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setStatus("PENDING"); // 默认待审核状态
        commentRepository.insert(comment);
        
        // 更新文章的评论数
        updateArticleCommentCount(comment.getArticleId());
        
        return comment;
    }

    @Override
    public Comment updateComment(Comment comment) {
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.updateById(comment);
        return comment;
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.selectById(id);
        if (comment != null) {
            commentRepository.deleteById(id);
            
            // 更新文章的评论数
            updateArticleCommentCount(comment.getArticleId());
        }
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.selectList(null);
    }

    @Override
    public List<Comment> findByStatus(String status) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return commentRepository.selectList(wrapper);
    }

    @Override
    public void approveComment(Long id) {
        Comment comment = commentRepository.selectById(id);
        if (comment != null) {
            comment.setStatus("APPROVED");
            comment.setUpdatedAt(LocalDateTime.now());
            commentRepository.updateById(comment);
            
            // 更新文章的评论数
            updateArticleCommentCount(comment.getArticleId());
        }
    }

    @Override
    public void rejectComment(Long id) {
        Comment comment = commentRepository.selectById(id);
        if (comment != null) {
            comment.setStatus("REJECTED");
            comment.setUpdatedAt(LocalDateTime.now());
            commentRepository.updateById(comment);
            
            // 更新文章的评论数
            updateArticleCommentCount(comment.getArticleId());
        }
    }
    
    private void updateArticleCommentCount(Long articleId) {
        // 这里应该调用ArticleService来更新文章的评论数
        // 由于循环依赖问题，可以使用Application Context获取Bean或通过其他方式实现
        // 简化实现：直接通过SQL更新
    }
}