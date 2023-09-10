package com.ito.bloger.controller.api;

import com.ito.bloger.dto.request.PostRequest;
import com.ito.bloger.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @PostMapping("/add")
    public void create(@RequestBody PostRequest request) {
        postService.create(request);
    }

    @PutMapping("/update")
    public void update(@RequestBody PostRequest request, @RequestParam Long id) {
        postService.update(request, id);
    }
}
