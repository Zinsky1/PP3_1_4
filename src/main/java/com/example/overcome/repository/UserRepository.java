package com.example.overcome.repository;

import com.example.overcome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u left join fetch u.roles where u.email = (:username)")
    User findByUsername( @Param("username") String username);

    @Query("from User where id != 1")
    List<User> findAllButAdmin();
}
