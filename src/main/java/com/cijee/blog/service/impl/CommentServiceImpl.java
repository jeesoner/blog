package com.cijee.blog.service.impl;

import com.cijee.blog.model.po.Comment;
import com.cijee.blog.repository.CommentRepository;
import com.cijee.blog.service.CommentService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cijee
 * @date 2020/7/3
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdTime");
        // 找到所有一级评论
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(id, sort);
        return eachComment(comments);
    }

    /**
     * 保存评论信息
     * 判断评论对象中的父对象是否为一级评论
     * 是-->  == -1
     *     这是一级评论
     * 不是--> != -1
     *     这是二级评论（回复）
     *
     * @param comment 评论对象
     * @return 评论对象
     */
    @Override
    public Comment saveComment(Comment comment) {
        // 判断前端的commentId是一级评论还是回复
        long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) { //
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        } else {
            comment.setParentComment(null); // 这是一级评论所以没有paraentComment对象，设置为空
        }
        comment.setCreatedTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 迭代遍历一级评论的子评论
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        // 先获取每个一级评论的所有的子评论
        if (comments == null || comments.size() == 0)
            return null;
        for (Comment root : comments) {
            List<Comment> list = new ArrayList<>(); // 子评论列表
            dfs(root, list);
            // 并将各自的子评论放在一级评论的replyComment中
            root.setReplyComments(list);
        }
        return comments;
    }

    /**
     * 深度搜索遍历
     *
     * @param root 一级评论
     * @param list 列表
     */
    private void dfs(Comment root, List<Comment> list) {
        List<Comment> replyComments = root.getReplyComments();
        if (replyComments.size() == 0) {
            return ;
        }
        for (Comment child : replyComments) {
            list.add(child);
            dfs(child, list);
        }
    }
}
