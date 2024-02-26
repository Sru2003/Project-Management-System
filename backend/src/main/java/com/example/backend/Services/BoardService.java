package com.example.backend.Services;

import com.example.backend.DTO.BoardDTO;
import com.example.backend.DTO.TaskCardDTO;
import com.example.backend.Entities.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    List<Board> getAllBoards();

    Optional<Board> getBoardById(Integer id);


    Optional<Board> getBoardByTitle(String title);
    Board saveNewBoard(BoardDTO boardDTO);
    Board updateBoard(Board oldBoard,BoardDTO newBoardDTO);

    void deleteBoard(Board board);

    Board addNewTaskToBoard(Integer id, TaskCardDTO taskCardDTO);

}
