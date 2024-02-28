package com.example.backend.Repository;

import com.example.backend.Entities.TaskCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskCardRepository extends JpaRepository<TaskCard,Integer> {
    Optional<TaskCard> findByName(String name);
}
