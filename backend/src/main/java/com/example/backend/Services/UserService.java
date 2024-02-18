package com.example.backend.Services;

import com.example.backend.DTO.LoginDto;
import com.example.backend.DTO.UserDto;
import com.example.backend.Entities.User;
import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
    //LoginMessage loginUser(LoginDto loginDto);
}
