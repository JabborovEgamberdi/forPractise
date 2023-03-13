package com.example.forphonenumberauthenticationusingkeycloak.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "__user")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String phoneNumber;

    @NotBlank
    private String password;

    @NotBlank
    @Column(unique = true)
    private String email;

}