package com.example.backend.Repository;

import com.example.backend.Entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    Board findByTitle(String name);
}
