package com.example.forsearch.repository.forSecurity;


import com.example.forsearch.entity.forSecurity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
