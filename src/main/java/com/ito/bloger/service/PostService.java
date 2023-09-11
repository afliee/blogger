package com.ito.bloger.service;

import com.ito.bloger.dto.request.CommentRequest;
import com.ito.bloger.dto.request.PostRequest;
import com.ito.bloger.enitty.Comment;
import com.ito.bloger.enitty.Post;
import com.ito.bloger.repository.CategoryRepository;
import com.ito.bloger.repository.CommentRepository;
import com.ito.bloger.repository.PostRepository;
import com.ito.bloger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
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
                .image(request.getImage())
                .sortDescription(request.getSortDescription())
                .category(category)
                .build();

        postRepository.save(post);
    }

    public void update(PostRequest request, Long id) {
        postRepository.findById(id).ifPresentOrElse(post -> {
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setImage(request.getImage());
            post.setSortDescription(request.getSortDescription());
            post.setCategory(categoryRepository.findById(Long.valueOf(request.getCategory())).orElseThrow(() -> new RuntimeException("Category not found")));
            postRepository.save(post);
        }, () -> {
            throw new RuntimeException("Post not found");
        });
    }

    public long updateViews(Long id) {
        postRepository.findById(id).ifPresentOrElse(post -> {
            post.setViews(post.getViews() + 1);
            postRepository.save(post);
        }, () -> {
            throw new RuntimeException("Post not found");
        });
        return postRepository.findById(id).get().getViews();
    }

    public void comment(Long postId, CommentRequest request) {
        postRepository.findById(postId).ifPresentOrElse(post -> {
            var author = userRepository.findById(Long.valueOf(request.getAuthor())).orElseThrow(() -> new RuntimeException("User not found"));

            var comment = Comment.builder()
                    .author(author)
                    .post(post)
                    .content(request.getContent())
                    .build();
            commentRepository.save(comment);
            post.getComments().add(comment);
            postRepository.save(post);
        }, () -> {
            throw new RuntimeException("Post not found");
        });
    }
    public List<Post> findAllPostLatest() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
