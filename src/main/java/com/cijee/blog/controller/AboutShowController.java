package com.cijee.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cijee
 * @date 2020/7/4
 */
@Controller
public class AboutShowController {

    /**
     * 关于页面
     *
     * @return 关于页面视图
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
