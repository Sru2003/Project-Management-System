package com.example.backend.Services.impl;

import com.example.backend.DTO.BoardDTO;
import com.example.backend.DTO.TaskCardDTO;
import com.example.backend.Entities.Board;
import com.example.backend.Entities.TaskCard;
import com.example.backend.Repository.BoardRepository;
import com.example.backend.Services.BoardService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    @Transactional
    public List<Board> getAllBoards() {
        List<Board> boardList=new ArrayList<>();
        boardRepository.findAll().forEach(boardList::add);
        return boardList;
    }

    @Override
    @Transactional
    public Optional<Board> getBoardById(Integer id) {
        return boardRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Board> getBoardByTitle(String title){
        return boardRepository.findByTitle(title);
    }
    @Override
    @Transactional
    public Board saveNewBoard(BoardDTO boardDTO) {
        return boardRepository.save(convertDTOToBoard(boardDTO));
    }

    @Override
    @Transactional
    public Board updateBoard(Board oldBoard, BoardDTO newBoardDTO) {
        oldBoard.setTitle(newBoardDTO.getTitle());
        oldBoard.setDescription(newBoardDTO.getDescription());
        return boardRepository.save(oldBoard);
    }

    @Override
    @Transactional
    public void deleteBoard(Board board) {boardRepository.delete(board);}

    @Override
    @Transactional
    public Board addNewTaskToBoard(Integer id, TaskCardDTO taskCardDTO) {
        Board board=boardRepository.findById(id).get();
        board.addTask(convertDTOToTask(taskCardDTO));
        return boardRepository.save(board);
    }

    private Board convertDTOToBoard(BoardDTO boardDTO){
        Board board =new Board();
        board.setTitle(boardDTO.getTitle());
        board.setDescription(boardDTO.getDescription());
        return board;
    }
    private TaskCard convertDTOToTask(TaskCardDTO taskCardDTO){
        TaskCard taskCard=new TaskCard();
        taskCard.setId(taskCardDTO.getId());
        taskCard.setName(taskCardDTO.getName());
        return taskCard;
    }
}
