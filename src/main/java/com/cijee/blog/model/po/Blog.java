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
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private String cover; // 图片
    private String flag; // 原创 转载
    private Integer views; // 浏览次数
    private boolean appreciation; // 赞赏功能
    private boolean shareStatement; // 分享功能
    private boolean commentable; // 评论功能
    private boolean published; // 版权
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @ManyToOne
    private User user;
    @ManyToOne
    private Type type;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();
}
