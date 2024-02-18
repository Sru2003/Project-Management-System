package com.example.backend.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="users_boards"
        ,joinColumns = {@JoinColumn(name="USER_ID",referencedColumnName="ID")},
        inverseJoinColumns = {@JoinColumn(name="BOARD_ID",referencedColumnName="ID")})
    private List<Board> boards=new ArrayList<>();

    public User() {
    }

    public User(Integer id, String name, String email, String password, List<Board> boards) {
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

}
