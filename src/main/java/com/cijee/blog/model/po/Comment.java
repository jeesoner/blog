package com.cijee.blog.model.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cijee
 * @date 2020/6/26
 */
@Data
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickName; // 昵称
    private String email; // 邮箱
    private String content; // 评论内容
    private String avatar; // 头像
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @ManyToOne
    private Blog blog;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;
}
