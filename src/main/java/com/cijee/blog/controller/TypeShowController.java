package com.cijee.blog.controller;

import com.cijee.blog.model.param.BlogParam;
import com.cijee.blog.model.po.Type;
import com.cijee.blog.service.BlogService;
import com.cijee.blog.service.TypeService;
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
public class TypeShowController {

    private final TypeService typeService;

    private final BlogService blogService;

    public TypeShowController(TypeService typeService, BlogService blogService) {
        this.typeService = typeService;
        this.blogService = blogService;
    }

    /**
     * 分类页面
     *
     * @return 分类页面视图
     */
    @GetMapping({"types", "/types/{id}"})
    public String types(@RequestParam(defaultValue = "0", required = false) Integer page,
                        @RequestParam(defaultValue = "8", required = false) Integer size,
                        @PathVariable(required = false) Long id, Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
        List<Type> types = typeService.listTypeTop(1000);
        if (id == null && types.size() != 0) {
            id = types.get(0).getId();
        }
        BlogParam blogParam = new BlogParam();
        blogParam.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogParam));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
