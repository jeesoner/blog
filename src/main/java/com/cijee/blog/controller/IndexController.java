package com.cijee.blog.controller;

import com.cijee.blog.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cijee
 * @date 2020/6/25
 */
@Controller
public class IndexController {

    @GetMapping("/{id}/{name}")
    public String index(@PathVariable Integer id, @PathVariable String name) {
        System.out.println(id + name);
        return "index";
    }
}
