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
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String nickName;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type; // 用户类型（角色）
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();
}
