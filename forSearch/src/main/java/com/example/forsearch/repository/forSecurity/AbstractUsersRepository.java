package com.example.forsearch.repository.forSecurity;

import com.example.forsearch.entity.forSecurity.AbstractUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbstractUsersRepository extends JpaRepository<AbstractUsers, Long> {
    boolean existsByUsername(String username);
    Optional<AbstractUsers> findByUsername(String username);
}