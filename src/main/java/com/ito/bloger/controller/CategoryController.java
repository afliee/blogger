package com.ito.bloger.controller;

import com.ito.bloger.enitty.Category;
import com.ito.bloger.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @PostMapping("/add")
    public String addCategory(
            @RequestBody String name
    ) {
        String categoryName = name.split("=")[1];
        var category = Category.builder()
                        .name(categoryName)
                        .build();
        categoryRepository.save(category);
        System.out.println(name);
        return "redirect:/admin/categories";
    }
}
