package com.example.overcome.Service;

import com.example.overcome.repository.UserRepository;
import com.example.overcome.model.User;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Service
public class UserServiceImp implements  UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private Session session;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        session.update(user);
        //userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    public List<User> findAllButAdmin(){
        return userRepository.findAllButAdmin();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
