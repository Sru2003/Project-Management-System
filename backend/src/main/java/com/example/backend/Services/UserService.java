package com.example.backend.Services;

import com.example.backend.DTO.BoardDTO;
import com.example.backend.DTO.UserDto;
import com.example.backend.Entities.Board;
import com.example.backend.Entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public User verifyUser(User s);

    public void deleteUser(String email);
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();

    Optional<User> getUserById(Long userId);

    User addNewBoardToUser(Long userId, BoardDTO boardDTO);
    //LoginMessage loginUser(LoginDto loginDto);
}
