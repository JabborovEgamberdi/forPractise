package com.example.forsearch.config;

import com.example.forsearch.entity.forSecurity.AbstractUsers;
import com.example.forsearch.entity.forSecurity.Role;
import com.example.forsearch.entity.forSecurity.RoleEnum;
import com.example.forsearch.entity.forSecurity.User;
import com.example.forsearch.repository.forSecurity.AbstractUsersRepository;
import com.example.forsearch.repository.forSecurity.RoleRepository;
import com.example.forsearch.repository.forSecurity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode; //always

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl; //create

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AbstractUsersRepository abstractUsersRepository;

    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always") && ddl.equals("create")) {
            Role admin = new Role();
            admin.setName(RoleEnum.ROLE_ADMIN);
            Role superadmin = new Role();
            superadmin.setName(RoleEnum.ROLE_SUPER_ADMIN);
            Role user_role = new Role();
            user_role.setName(RoleEnum.ROLE_USER);

            roleRepository.save(admin);
            roleRepository.save(superadmin);
            roleRepository.save(user_role);


            Set<Role> roles = new HashSet<>();
            roles.add(admin);
            roles.add(superadmin);
            roles.add(user_role);


            User user = new User();
            user.setUsername("JabborovEgamberdi");
            user.setRoles(superadmin);
            user.setLastName("Jabborov");
            user.setFirstName("Egamberdi");
            user.setPassword(passwordEncoder.encode("2002"));
            userRepository.save(user);

            AbstractUsers abstractUsers = new AbstractUsers();
            abstractUsers.setEnabled(true);
            abstractUsers.setUsername(user.getUsername());
            abstractUsers.setPassword(2002);
            abstractUsersRepository.save(abstractUsers);


            User user1 = new User();
            user1.setUsername("KarimovBekhruz");
            user1.setRoles(admin);
            user1.setLastName("Karimov");
            user1.setFirstName("Behhruz");
            user1.setPassword(passwordEncoder.encode("2003"));
            userRepository.save(user1);

            AbstractUsers abstractUsers1 = new AbstractUsers();
            abstractUsers1.setEnabled(true);
            abstractUsers1.setUsername(user1.getUsername());
            abstractUsers1.setPassword(2003);
            abstractUsersRepository.save(abstractUsers1);


            User user2 = new User();
            user2.setUsername("KeldiyarovAkhrorbek");
            user2.setRoles(user_role);
            user2.setLastName("Keldiyarov");
            user2.setFirstName("Akhrorbek");
            user2.setPassword(passwordEncoder.encode("2004"));
            userRepository.save(user2);

            AbstractUsers abstractUsers2 = new AbstractUsers();
            abstractUsers2.setEnabled(true);
            abstractUsers2.setUsername(user2.getUsername());
            abstractUsers2.setPassword(2004);
            abstractUsersRepository.save(abstractUsers2);

        }
    }

}