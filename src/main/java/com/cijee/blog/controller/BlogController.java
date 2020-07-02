package com.cijee.blog.controller;

import com.cijee.blog.model.param.BlogParam;
import com.cijee.blog.model.po.Blog;
import com.cijee.blog.model.po.Tag;
import com.cijee.blog.model.po.Type;
import com.cijee.blog.model.po.User;
import com.cijee.blog.service.BlogService;
import com.cijee.blog.service.TagService;
import com.cijee.blog.service.TypeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author cijee
 * @date 2020/6/27
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String WRITE = "admin/write";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    private final BlogService blogService;
    private final TypeService typeService;
    private final TagService tagService;

    public BlogController(BlogService blogService, TypeService typeService, TagService tagService) {
        this.blogService = blogService;
        this.typeService = typeService;
        this.tagService = tagService;
    }

    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "0", required = false) Integer page,
                        @RequestParam(defaultValue = "10", required = false) Integer size,
                        BlogParam blogParam, Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("page", blogService.listBlog(pageable, blogParam));
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    /**
     * 博客搜索
     *
     * @param page 页码
     * @param size 页大小
     * @param blog 参数
     * @param model model
     * @return 返回给blogList数据
     */
    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "0", required = false) Integer page,
                         @RequestParam(defaultValue = "10", required = false) Integer size,
                         BlogParam blog, Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    /**
     * 跳转到写博客页面
     *
     * @param model
     * @return
     */
    @GetMapping("/blogs/write")
    public String write(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return WRITE;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, String tagIds, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        // 获取分类id
        blog.setType(typeService.getType(blog.getType().getId()));
        // 获取标签集
        blog.setTags(tagService.listTag(tagIds));
        // 保存到数据库
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    @PostMapping("/blogs/update")
    public String update(Blog blog, String tagIds, RedirectAttributes attributes, HttpSession session) {
        // 获取分类id
        blog.setType(typeService.getType(blog.getType().getId()));
        // 获取标签集
        blog.setTags(tagService.listTag(tagIds));
        // 保存到数据库
        Blog b = blogService.updateBlog(blog.getId(), blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/write")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blog", blog);
        model.addAttribute("tagIds", tagsToIds(blog.getTags()));
        return WRITE;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.removeBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {// [1,2,3,4,5]
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < tags.size(); i++) {
                ids.append(tags.get(i).getId());
                if (i < tags.size() - 1) {
                    ids.append(",");
                }
            }
            return ids.toString();
        } else {
            return null;
        }
    }
}
