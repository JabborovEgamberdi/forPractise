package com.example.forsearch.repository.forSecurity;


import com.example.forsearch.entity.forSecurity.Role;
import com.example.forsearch.entity.forSecurity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}