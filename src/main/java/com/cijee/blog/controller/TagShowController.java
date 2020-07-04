package com.cijee.blog.controller;

import com.cijee.blog.model.param.BlogParam;
import com.cijee.blog.model.po.Tag;
import com.cijee.blog.service.BlogService;
import com.cijee.blog.service.TagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author cijee
 * @date 2020/7/3
 */
@Controller
public class TagShowController {

    private final BlogService blogService;
    private final TagService tagService;

    public TagShowController(BlogService blogService, TagService tagService) {
        this.blogService = blogService;
        this.tagService = tagService;
    }

    /**
     * 分类页面
     *
     * @return 分类页面视图
     */
    @GetMapping({"/tags","/tags/{id}"})
    public String types(@RequestParam(defaultValue = "0", required = false) Integer page,
                        @RequestParam(defaultValue = "8", required = false) Integer size,
                        @PathVariable(required = false) Long id, Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
        List<Tag> tags = tagService.listTagTop(1000);
        if (id == null && tags.size() != 0) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId", id);
        System.out.println(tags.size());
        return "tags";
    }
}
