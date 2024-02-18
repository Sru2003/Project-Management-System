package com.example.backend.Repository;

import com.example.backend.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    
}
