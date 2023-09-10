package com.ito.bloger.enitty;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Token extends Base<Long> {
    private String token;
    private boolean isRevoked;
    private boolean isExpired;
    @ManyToOne(
            targetEntity = User.class,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
}
