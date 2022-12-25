package com.example.forsearch.entity.forSecurity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

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