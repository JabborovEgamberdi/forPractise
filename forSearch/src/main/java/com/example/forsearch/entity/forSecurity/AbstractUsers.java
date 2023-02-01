package com.example.forsearch.entity.forSecurity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class AbstractUsers extends AbsEntity {

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private Integer password;

    private boolean enabled = false;

}