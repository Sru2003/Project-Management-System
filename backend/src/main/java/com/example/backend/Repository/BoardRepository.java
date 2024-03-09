package com.example.backend.Repository;

import com.example.backend.Entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Optional<Board> findByTitle(String name);


}
