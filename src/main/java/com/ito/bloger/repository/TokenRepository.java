package com.ito.bloger.repository;

import com.ito.bloger.enitty.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    @Query("SELECT t FROM Token t WHERE t.user.id = ?1 AND (t.isExpired = false OR t.isRevoked = false)")
    List<Token> findAllValidTokenByUserId(Long userId);
}
