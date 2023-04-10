package com.example.overcome;

import com.example.overcome.model.Role;
import com.example.overcome.model.User;
import com.example.overcome.repository.UserRepository;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Component
public class AppRunner implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AppRunner(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User admin1 = new User();
        admin1.setFirstname("admin");
        admin1.setLastname("admin");
        admin1.setAge(21);
        admin1.setEmail("admin@gmail.com");
        admin1.setPassword(passwordEncoder.encode("admin"));
        admin1.setRoles(Set.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN")));
        userRepository.save(admin1);

    }
}
