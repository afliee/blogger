package com.ito.bloger.repository;

import com.ito.bloger.enitty.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitleIgnoreCase(String title);
    List<Post> findAllByOrderByCreatedDateDesc();
}
