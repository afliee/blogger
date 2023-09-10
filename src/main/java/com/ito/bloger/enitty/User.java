package com.ito.bloger.enitty;

import com.ito.bloger.config.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"tokens", "posts", "comments"})
public class User extends Base<Long> implements UserDetails {
    private String username;
    private String password;
    private String fullName;
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(
            targetEntity = Token.class,
            cascade = CascadeType.ALL
    )
    private List<Token> tokens;

    @OneToMany(
            targetEntity = Post.class,
            mappedBy = "author"
    )
    private List<Post> posts;

    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "author"
    )
    private List<Comment> comments = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role.authority());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
