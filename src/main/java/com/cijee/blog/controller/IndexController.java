package com.cijee.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author saykuray
 * @date 2020/6/25
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        // int a = 1/ 0;
        return "index";
    }
}
