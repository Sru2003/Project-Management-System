package com.example.backend.Repository;

import com.example.backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {
    //Optional<User> findOneByEmailAndPassword(String email,String password);
    @Query("from User where email=?1")
    User findByEmail(String email);
    @Query("from User where email=?1 and password=?2")
    public User findByUsernamePassword(String username,String password);

}
