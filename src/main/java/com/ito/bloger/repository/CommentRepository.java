package com.ito.bloger.repository;

import com.ito.bloger.enitty.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
