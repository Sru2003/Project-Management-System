package com.example.backend.Controller;

import com.example.backend.DTO.BoardDTO;
import com.example.backend.DTO.TaskCardDTO;
import com.example.backend.Entities.Board;
import com.example.backend.Services.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping("/")
    public ResponseEntity<?> getAllBoards(){
        try{
            return new ResponseEntity<>(boardService.getAllBoards(), HttpStatus.OK);
        }
        catch (Exception e){
            return errorResponse();
        }
    }

    @RequestMapping("/{title}")
    public ResponseEntity<?> getBoardByTitle(@PathVariable String title)
    {
        try{
            Optional<Board> optionalBoard=boardService.getBoardByTitle(title);
            if(optionalBoard.isPresent()) {
                return new ResponseEntity<>(
                        optionalBoard.get(),
                        HttpStatus.OK
                );
            }else{
                    return new ResponseEntity<>("No board found with title: "+title,HttpStatus.NOT_FOUND);
                }

        }catch (Exception e){
            return errorResponse();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createBoard(@RequestBody BoardDTO boardDTO){
        try{
            return new ResponseEntity<>(
                    boardService.saveNewBoard(boardDTO),
                    HttpStatus.CREATED);
        }catch (Exception e){
            return errorResponse();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Integer id,@RequestBody BoardDTO boardDTO){
        try{
            Optional<Board> optionalBoard=boardService.getBoardById(id);
            if(optionalBoard.isPresent()){
                return new ResponseEntity<>(boardService.updateBoard(optionalBoard.get(),boardDTO),HttpStatus.OK);
            }else {
                return noBoardFoundResponse(id);
            }
        }catch (Exception e){
            return errorResponse();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Integer id){
        try{
            Optional<Board> optionalBoard=boardService.getBoardById(id);
            if(optionalBoard.isPresent()){
                boardService.deleteBoard(optionalBoard.get());
                return new ResponseEntity<>(
                        String.format("Board with id: %d was deleted",id),
                        HttpStatus.OK);
            }
            else{
                return noBoardFoundResponse(id);
            }
        }catch (Exception e){
            return noBoardFoundResponse(id);
        }
    }

    @GetMapping("/{boardId}/tasks/")
    public ResponseEntity<?> getAllTasksInBoard(@PathVariable Integer boardId){
        try {
            Optional<Board> optionalBoard=boardService.getBoardById(boardId);
            if(optionalBoard.isPresent()){
                return new ResponseEntity<>(
                        optionalBoard.get().getTaskCards(),
                        HttpStatus.OK);
            }else {
                return noBoardFoundResponse(boardId);
            }
        }catch (Exception e){
            return errorResponse();
        }
    }

    @PostMapping("{boardId}/tasks/")
    public ResponseEntity<?> createTaskAssignedToBoard(@PathVariable Integer boardId, @RequestBody TaskCardDTO taskCardDTO){
        try {
            return new ResponseEntity<>(
                    boardService.addNewTaskToBoard(boardId,taskCardDTO),
                    HttpStatus.CREATED);

        }catch (Exception e){
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse() {
        return  new ResponseEntity<>("Something went wrong : (",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String>noBoardFoundResponse(Integer id){
        return new ResponseEntity<>("No Board found with id: " + id,HttpStatus.NOT_FOUND);
    }
}
