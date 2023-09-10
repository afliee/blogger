package com.ito.bloger.service;

import com.ito.bloger.enitty.Token;
import com.ito.bloger.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws RuntimeException {
        String authorizationHeader = request.getHeader("Authorization");
        String token;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            log warning
            System.out.println("Authorization header is null or does not start with Bearer");
            throw  new RuntimeException("Authorization header is null or does not start with Bearer");
        }

        token = authorizationHeader.substring("Bearer ".length());

        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }

        var tokenCookie = WebUtils.getCookie(request, "token");
        if (tokenCookie != null && !tokenCookie.getValue().isEmpty()) {
            tokenCookie.setValue(null);
            tokenCookie.setMaxAge(0);
            tokenCookie.setPath("/");
            tokenCookie.setHttpOnly(true);
            response.addCookie(tokenCookie);
        }
    }
}
