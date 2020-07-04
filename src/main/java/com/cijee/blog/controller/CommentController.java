package com.cijee.blog.controller;

import com.cijee.blog.model.po.Comment;
import com.cijee.blog.model.po.User;
import com.cijee.blog.service.BlogService;
import com.cijee.blog.service.CommentService;
import com.cijee.blog.util.AvatarUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author cijee
 * @date 2020/7/3
 */
@Controller
public class CommentController {

    private final CommentService commentService;

    private final BlogService blogService;

    public CommentController(CommentService commentService, BlogService blogService) {
        this.commentService = commentService;
        this.blogService = blogService;
    }

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    /**
     * 提交评论信息
     *
     * @param comment 评论对象
     * @return 新的页面
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        // 因为从前台获取到的blog comment都是只有一个id值，所以要重新去数据库中查询整个对象
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        // 管理员评论
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickName(user.getNickName());
        } else { // 游客评论
            // 给游客设置随机头像
            comment.setAvatar(AvatarUtils.getRandomAvatar());
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
