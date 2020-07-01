package com.cijee.blog.service;

import com.cijee.blog.model.po.Blog;
import com.cijee.blog.model.param.BlogParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author cijee
 * @date 2020/6/29
 */
public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogParam blogParam);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void removeBlog(Long id);
}
