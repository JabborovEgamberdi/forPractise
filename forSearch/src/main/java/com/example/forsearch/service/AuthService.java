package com.example.forsearch.service;

import com.example.forsearch.config.JwtProvider;
import com.example.forsearch.entity.forSecurity.Role;
import com.example.forsearch.entity.forSecurity.RoleEnum;
import com.example.forsearch.entity.forSecurity.User;
import com.example.forsearch.payload.forSecurity.ApiResponse;
import com.example.forsearch.payload.forSecurity.LoginDTO;
import com.example.forsearch.payload.forSecurity.RegisterDTO;
import com.example.forsearch.repository.forSecurity.AbstractUsersRepository;
import com.example.forsearch.repository.forSecurity.RoleRepository;
import com.example.forsearch.repository.forSecurity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AbstractUsersRepository abstractUsersRepository;

    @Autowired
    JwtProvider jwtProvider;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public ApiResponse<?> register(RegisterDTO registerDTO) {
        //     if (abstractUsersRepository.existsByUsername(registerDTO.getUsername())) {
//            if (!abstractUsersRepository.findByUsername(registerDTO.getUsername()).get().isEnabled()) {
//                return new ApiResponse<>("The user did not confirm the password sent", false);
//            }
//        } else {
//            return new ApiResponse<>("This user not found", false);
//        }

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByName(RoleEnum.ROLE_ADMIN).get());


        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRoles(roleRepository.findByName(RoleEnum.ROLE_ADMIN).get());
        userRepository.save(user);

        String token = jwtProvider.generateToken(user.getUsername());


        return new ApiResponse<>("Successfully registered", true, token);
    }

    public ApiResponse<?> login(LoginDTO dto) {
        Optional<User> byUserName = userRepository.findByUsername(dto.getUsername());
        if (byUserName.isPresent()) {
            User user = byUserName.get();
            if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                String token = jwtProvider.generateToken(dto.getUsername());
                return new ApiResponse<>("Success", true, token);
            } else {
                return new ApiResponse<>("Password is failed", false);
            }
        }
        return new ApiResponse<>("User not found", false);
    }

    public ApiResponse<?> editRole(Integer id, User user) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            user.setRoles(optionalRole.get());
            userRepository.save(user);
            return new ApiResponse<>("User role updated successfully", true);
        }
        return new ApiResponse<>("There is no user with this id", false);
    }

}