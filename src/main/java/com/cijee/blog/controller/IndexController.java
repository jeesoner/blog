package com.cijee.blog.controller;

import com.cijee.blog.model.param.BlogParam;
import com.cijee.blog.service.BlogService;
import com.cijee.blog.service.TagService;
import com.cijee.blog.service.TypeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author cijee
 * @date 2020/6/25
 */
@Controller
public class IndexController {

    private final BlogService blogService;

    private final TypeService typeService;

    private final TagService tagService;

    public IndexController(BlogService blogService, TypeService typeService, TagService tagService) {
        this.blogService = blogService;
        this.typeService = typeService;
        this.tagService = tagService;
    }

    /**
     * 返回首页数据
     *
     * @param page 页码
     * @param size 页大小
     * @param model model
     * @return 首页视图
     */
    @GetMapping({"/", "/index"})
    public String index(@RequestParam(defaultValue = "0", required = false) Integer page,
                        @RequestParam(defaultValue = "10", required = false) Integer size,
                        Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    /**
     * 分类页面
     *
     * @return 分类页面视图
     */
    @GetMapping("/types")
    public String types() {
        return "types";
    }

    /**
     * 标签页面
     *
     * @return 标签页面视图
     */
    @GetMapping("/tags")
    public String tags() {
        return "tags";
    }

    /**
     * 归档页面
     *
     * @return 归档页面视图
     */
    @GetMapping("/archives")
    public String archives() {
        return "archives";
    }

    /**
     * 关于页面
     *
     * @return 关于页面视图
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    /**
     * 搜索
     *
     * @param page 页码
     * @param size 页大小
     * @param query 查询参数
     * @param model model
     * @return 搜索结果页
     */
    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "0", required = false) Integer page,
                         @RequestParam(defaultValue = "8", required = false) Integer size,
                         @RequestParam String query,  Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
        model.addAttribute("page", blogService.listBlog(query, pageable));
        model.addAttribute("query", query);
        return "search";
    }


}
