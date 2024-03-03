package com.example.backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String title;

    public Board(Integer id, String title, String description, List<User> users, List<TaskCard> taskCards) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.users = users;
        this.taskCards = taskCards;
    }

    @Column(nullable = false)
    private String description;

    @JsonIgnoreProperties("boards")
    @ManyToMany(mappedBy = "boards")
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<TaskCard> taskCards ;
    public Board() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<TaskCard> getTaskCards() {
        return taskCards;
    }

    public void setTaskCards(List<TaskCard> taskCards) {
        this.taskCards = taskCards;
    }

    public void addTask(TaskCard taskCard) {

        if (Objects.isNull(taskCards)) {
            taskCards = new ArrayList<>();
        }
        taskCards.add(taskCard);
    }
}
