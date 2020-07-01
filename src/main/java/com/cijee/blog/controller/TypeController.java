package com.cijee.blog.controller;

import com.cijee.blog.model.po.Type;
import com.cijee.blog.service.TypeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author cijee
 * @date 2020/6/28
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "0", required = false) Integer page,
                        @RequestParam(defaultValue = "10", required = false) Integer size,
                        Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "/admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "分类名称已存在");
            return "admin/types-input";
        }
        type1 = typeService.saveType(type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/update")
    public String update(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "分类名称已存在");
            return "admin/types-input";
        }
        type1 = typeService.updateType(type.getId(), type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "/admin/types-input";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.removeType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
