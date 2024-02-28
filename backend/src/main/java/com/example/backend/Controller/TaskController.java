package com.example.backend.Controller;

import com.example.backend.DTO.TaskCardDTO;
import com.example.backend.Entities.TaskCard;
import com.example.backend.Services.TaskCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {
    private final TaskCardService taskCardService;

    public TaskController(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    @GetMapping("/")
    public ResponseEntity<?>getAllTasks(){
        try{
            return new ResponseEntity<>(
                    taskCardService.getAllTasks(),
                    HttpStatus.OK
            );
        }catch (Exception e){
            return errorResponse();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Integer id){
        try {
            Optional<TaskCard> optionalTaskCard=taskCardService.getTaskById(id);
            if(optionalTaskCard.isPresent()){
                return new ResponseEntity<>(optionalTaskCard.get(),HttpStatus.OK);
            }else {
                return noTaskFoundResponse(id);
            }
        }catch (Exception e){
            return noTaskFoundResponse(id);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getTaskByTitle(@RequestParam String title){
        try{
            Optional<TaskCard> optionalTaskCard= taskCardService.getTaskByTitle(title);
            if(optionalTaskCard.isPresent()){
                return new ResponseEntity<>(optionalTaskCard.get(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>("No task found with a title: "+ title,HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return errorResponse();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createTask(@RequestBody TaskCardDTO taskCardDTO){
        try {
            return new ResponseEntity<>(
                    taskCardService.saveNewTask(taskCardDTO),
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            return errorResponse();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id,@RequestBody TaskCardDTO taskCardDTO){
        try {
            Optional<TaskCard> optionalTaskCard=taskCardService.getTaskById(id);
            if(optionalTaskCard.isPresent()){
                return new ResponseEntity<>(taskCardService.updateTask(optionalTaskCard.get(),taskCardDTO),
                        HttpStatus.OK);
            }
            else {
                return noTaskFoundResponse(id);
            }
        }catch (Exception e){
            return errorResponse();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id){
        try{
            Optional<TaskCard> optionalTask=taskCardService.getTaskById(id);
            if(optionalTask.isPresent()){
                taskCardService.deleteTask(optionalTask.get());
                return new ResponseEntity<>(String.format("Task with id :%d was deleted",id),HttpStatus.OK);
            }
            else {
                return noTaskFoundResponse(id);
            }
        }catch (Exception e){
            return errorResponse();
        }
    }
    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something went wrong:(",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<String> noTaskFoundResponse(Integer id){
        return new ResponseEntity<>("no task found with id: "+id,HttpStatus.NOT_FOUND);
    }
}
