package com.ito.bloger.enitty;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"comments", "author", "category"})
@EntityListeners(AuditingEntityListener.class)
public class Post extends Base<Long> {
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String image;

    @ManyToOne(
            targetEntity = User.class,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    private User author;

    @ManyToOne(
            targetEntity = Category.class,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
    )
    private Category category;

    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "post",
            cascade = CascadeType.ALL
    )
    private Set<Comment> comments = new HashSet<>();
    private String sortDescription;
}
