package com.ito.bloger.controller;

import com.ito.bloger.enitty.Post;
import com.ito.bloger.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
    private final PostRepository postRepository;

    @RequestMapping("")
    public String index(Model model) {
        var postsAll = postRepository.findAll();
        var slider = postsAll.stream().limit(3).toList();
        List<Post> posts;
        if (postsAll.size() - 3 > 4) {
            posts = postRepository.findAll().stream().skip(3).limit(4).toList();
        } else {
            posts = postRepository.findAll().stream().skip(3).limit(postsAll.size() - 3).toList();
        }

        var latestPost = postRepository.findAllByOrderByCreatedDateDesc().stream().limit(3).toList();
        model.addAttribute("latestPost", latestPost);
        model.addAttribute("slider", slider);
        model.addAttribute("posts", posts);

        return "pages/index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "pages/home/contact";
    }

    @GetMapping("/blog")
    public String blog(
            Model model
    ) {

        return "pages/home/blog";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/home/login";
    }
}
