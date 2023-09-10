package com.ito.bloger.controller;

import com.ito.bloger.enitty.User;
import com.ito.bloger.repository.CategoryRepository;
import com.ito.bloger.repository.PostRepository;
import com.ito.bloger.service.JwtService;
import com.ito.bloger.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @RequestMapping
    public String admin() {
        return "pages/admin/index";
    }

    @GetMapping("/categories")
    public String categories() {
        return "pages/admin/categories";
    }

    @GetMapping("/post")
    public String post(
            @RequestParam(name = "filter", defaultValue = "all", required = false) String filter,
            HttpServletRequest request,
            Model model
    ) {
        var posts = postRepository.findAll();
        var categories = categoryRepository.findAll();
        Cookie token = WebUtils.getCookie(request, "token");
        if (token != null){
            String username = jwtService.extractUsername(token.getValue());
            System.out.println(username);
            User user = (User) userService.loadUserByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("posts", posts);
        return "pages/admin/post";
    }
}
