package com.ito.bloger.controller.api;

import com.ito.bloger.dto.request.CommentRequest;
import com.ito.bloger.dto.request.PostRequest;
import com.ito.bloger.service.PostService;
import jakarta.validation.Valid;
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

    @PutMapping("/update/views")
    public long updateViews(@RequestParam Long id) {
        return postService.updateViews(id);
    }

    @PostMapping("/comment")
    public void comment(
           @RequestParam Long postId,
           @RequestBody @Valid CommentRequest request
    ) {
        postService.comment(postId, request);
    }
}
