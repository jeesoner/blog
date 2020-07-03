package com.cijee.blog.service;

import com.cijee.blog.model.po.Comment;

import java.util.List;

/**
 * @author cijee
 * @date 2020/7/3
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);
}
