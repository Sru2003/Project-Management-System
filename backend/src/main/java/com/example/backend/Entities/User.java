package com.example.backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private  String name;

    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    @JsonIgnoreProperties("users")
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="users_boards"
        ,joinColumns = {@JoinColumn(name="USER_ID",referencedColumnName="ID")},
        inverseJoinColumns = {@JoinColumn(name="BOARD_ID",referencedColumnName="ID")})
    private List<Board> boards=new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String email, String password, List<Board> boards) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.boards = boards;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addBoard(Board board) {

        if (Objects.isNull(boards)) {
            boards = new ArrayList<>();
        }
        boards.add(board);
    }
}
