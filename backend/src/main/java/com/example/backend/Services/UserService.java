package com.example.backend.Services;

import com.example.backend.DTO.UserDto;
import com.example.backend.Entities.User;
import java.util.List;

public interface UserService {
    public User verifyUser(User s);
    public User createUser(User s);
    public User updateUser(User s);
    public void deleteUser(String email);
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
    //LoginMessage loginUser(LoginDto loginDto);
}
