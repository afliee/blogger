package com.ito.bloger.enitty;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Category extends Base<Long> {
    private String name;
    private String description;

    @OneToMany(
            targetEntity = Post.class,
            mappedBy = "category"
    )
    private Set<Post> posts = new HashSet<>();
}
