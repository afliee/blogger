package com.ito.bloger.enitty;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "comments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Comment extends Base<Long> {
    private String content;

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
            targetEntity = Post.class,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id"
    )
    private Post post;
}
