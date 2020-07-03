package com.cijee.blog.service.impl;

import com.cijee.blog.exception.NotFoundException;
import com.cijee.blog.model.param.BlogParam;
import com.cijee.blog.model.po.Blog;
import com.cijee.blog.model.po.Type;
import com.cijee.blog.repository.BlogRepository;
import com.cijee.blog.service.BlogService;
import com.cijee.blog.util.MarkdownUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cijee
 * @date 2020/6/29
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    /**
     * 获取单个Blog对象
     * 将Blog的Markdown内容转换成Html格式
     *
     * @param id 主键
     * @return 查询结果，查询不到返回null
     */
    @Override
    public Blog getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return blog;
    }

    /**
     * 根据查询的参数动态拼接SQL，获取分页结果
     *
     * @param pageable 分页参数
     * @param blog 查询参数
     * @return 分页结果
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogParam blog) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(blog.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    /**
     * 获取博客分页
     *
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * 获取搜索分页结果
     *
     * @param query 待查询的关键字
     * @param pageable 参数参数
     * @return 分页集合
     */
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery("%" + query + "%", pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    /**
     * 获取热门推荐
     *
     * @param topSize 条数
     * @return 博客集合
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer topSize) {
        Pageable pageable = PageRequest.of(0, topSize, Sort.by(Sort.Direction.DESC, "updatedTime"));
        return blogRepository.findTop(pageable);
    }

    /**
     * 保存博客
     *
     * @param blog 博客对象
     * @return 保存后的结果
     */
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreatedTime(new Date());
        blog.setUpdatedTime(new Date());
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    /**
     * 更新博客
     *
     * @param id 主键
     * @param blog 旧的博客对象
     * @return 新的博客对象
     */
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElse(null);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        // 将数据库中查到的博客对象的一些字段赋值到新的博客对象中
        // 因为直接更新的话，会导致新的对象会覆盖数据库中无需修改的那些字段
        blog.setUpdatedTime(new Date());
        blog.setCreatedTime(b.getCreatedTime());
        blog.setUser(b.getUser());
        blog.setViews(b.getViews());
        return blogRepository.save(blog);
    }

    /**
     * 删除博客
     * @param id 主键
     */
    @Override
    public void removeBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
