package com.example.backend.Services;

import com.example.backend.DTO.TaskCardDTO;
import com.example.backend.Entities.TaskCard;

import java.util.List;
import java.util.Optional;

public interface TaskCardService {
    List<TaskCard> getAllTasks();
    Optional<TaskCard> getTaskById(Integer id);
    Optional<TaskCard> getTaskByTitle(String title);
    TaskCard saveNewTask(TaskCardDTO taskCardDTO);
    TaskCard updateTask(TaskCard oldTask,TaskCardDTO taskCardDTO);
    void deleteTask(TaskCard taskCard);

}
