package com.ito.bloger.controller;

import com.ito.bloger.dto.request.PostRequest;
import com.ito.bloger.repository.CategoryRepository;
import com.ito.bloger.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/delete")
    public void delete(@RequestParam Long id) {
        postService.delete(id);
    }

    @GetMapping("/edit")
    public String edit(
            @RequestParam Long id,
            Model model
    ) {
        var categoies = categoryRepository.findAll();
        var post = postService.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("categories", categoies);
        return "pages/admin/edit_post";
    }
}
