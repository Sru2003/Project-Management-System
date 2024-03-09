package com.example.backend.Services.impl;

import com.example.backend.DTO.TaskCardDTO;
import com.example.backend.Entities.TaskCard;
import com.example.backend.Repository.TaskCardRepository;
import com.example.backend.Services.TaskCardService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskCardService {
    @PersistenceContext
    private EntityManager entityManager;


    private  final TaskCardRepository taskCardRepository;

    public TaskServiceImpl(TaskCardRepository taskCardRepository) {
        this.taskCardRepository = taskCardRepository;
    }

    @Override
    @Transactional
    public List<TaskCard> getAllTasks() {
        List<TaskCard> taskCardList=new ArrayList<>();
        taskCardRepository.findAll().forEach(taskCardList::add);
        return taskCardList;
    }

    @Override
    @Transactional
    public Optional<TaskCard> getTaskById(Long id) {
        return taskCardRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<TaskCard> getTaskByTitle(String title) {
        return taskCardRepository.findByName(title);
    }

    @Override
    @Transactional
    public TaskCard saveNewTask(TaskCardDTO taskCardDTO) {
        return taskCardRepository.save(convertDTOToTask(taskCardDTO));
    }

    @Override
    @Transactional
    public TaskCard updateTask(TaskCard oldTask, TaskCardDTO taskCardDTO) {
        return taskCardRepository.save(updateTaskFromDTO(oldTask,taskCardDTO));
    }

    @Override
    @Transactional
    public void deleteTaskAssociations(Long boardId) {
        Query query = entityManager.createNativeQuery("DELETE FROM board_task_cards WHERE task_cards_id = ?1");
        query.setParameter(1, boardId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteTask(TaskCard taskCard) {
            taskCardRepository.delete(taskCard);
    }

    private TaskCard convertDTOToTask(TaskCardDTO taskCardDTO){
        TaskCard taskCard=new TaskCard();
        taskCard.setName(taskCardDTO.getName());
        taskCard.setStatus(taskCardDTO.getStatus());
        taskCard.setStartDate(taskCardDTO.getStartDate());
        taskCard.setEndDate(taskCardDTO.getEndDate());
        return taskCard;
    }

    private TaskCard updateTaskFromDTO(TaskCard taskCard, TaskCardDTO taskCardDTO){
        if(Optional.ofNullable((taskCardDTO.getName())).isPresent()) {
            {
                taskCard.setName(taskCardDTO.getName());
            }
        }
        return taskCard;
    }
}
