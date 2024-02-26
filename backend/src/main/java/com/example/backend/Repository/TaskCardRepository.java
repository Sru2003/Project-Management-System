package com.example.backend.Repository;

import com.example.backend.Entities.TaskCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCardRepository extends JpaRepository {
    TaskCard findByName(String name);
}
