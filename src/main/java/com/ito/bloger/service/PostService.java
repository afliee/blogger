package com.ito.bloger.service;

import com.ito.bloger.dto.request.PostRequest;
import com.ito.bloger.enitty.Post;
import com.ito.bloger.repository.CategoryRepository;
import com.ito.bloger.repository.PostRepository;
import com.ito.bloger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void create(PostRequest request) {
        postRepository.findByTitleIgnoreCase(request.getTitle()).ifPresent(post -> {
            throw new RuntimeException("Post with this title already exists");
        });


        var author = userRepository.findById(Long.valueOf(request.getAuthor())).orElseThrow(() -> new RuntimeException("User not found"));
        var category = categoryRepository.findById(Long.valueOf(request.getCategory())).orElseThrow(() -> new RuntimeException("Category not found"));

        var post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(author)
                .category(category)
                .build();

        postRepository.save(post);
    }

    public void update(PostRequest request, Long id) {
        postRepository.findById(id).ifPresentOrElse(post -> {
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setCategory(categoryRepository.findById(Long.valueOf(request.getCategory())).orElseThrow(() -> new RuntimeException("Category not found")));
            postRepository.save(post);
        }, () -> {
            throw new RuntimeException("Post not found");
        });
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}