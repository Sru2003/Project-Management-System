package com.example.backend.Repository;

import com.example.backend.Entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Integer> {

    Optional<Board> findByTitle(String name);


}
