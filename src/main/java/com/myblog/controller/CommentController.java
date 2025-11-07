package com.myblog.controller;

import com.myblog.entity.Comment;
import com.myblog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<Comment>> getCommentsByArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(commentService.findByArticleIdAndStatus(articleId, "APPROVED"));
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        comment.setId(id);
        Comment updatedComment = commentService.updateComment(comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().body("评论删除成功");
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Comment>> getCommentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(commentService.findByStatus(status));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveComment(@PathVariable Long id) {
        commentService.approveComment(id);
        return ResponseEntity.ok().body("评论已批准");
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectComment(@PathVariable Long id) {
        commentService.rejectComment(id);
        return ResponseEntity.ok().body("评论已拒绝");
    }
}