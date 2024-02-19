package com.example.backend.Services.impl;

import com.example.backend.DTO.UserDto;
import com.example.backend.Entities.User;
import com.example.backend.Repository.BoardRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;



    public UserServiceImpl(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;

    }


    @Override
    public User verifyUser(User s) {
        return null;
    }

    @Override
    public User createUser(User s) {
        return null;
    }

    @Override
    public User updateUser(User s) {
        return null;
    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public void saveUser(UserDto userDto) {
        User user=new User();
        user.setName(userDto.getFirstName() + " "+userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
    }
    @Override
    public User findUserByEmail(String email) {
               return userRepository.findByEmail(email);
    }
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }



//
//    @Override
//    public LoginMessage loginUser(LoginDto loginDTO) {
//        String msg = "";
//        User user = userRepository.findByEmail(loginDTO.getEmail());
//        if (user != null) {
//            String password = loginDTO.getPassword();
//            String encodedPassword = user.getPassword();
//            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//            if (isPwdRight) {
//                Optional<User> user1 = userRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
//                if (user1.isPresent()) {
//                    return new LoginMessage("Login Success", true);
//                } else {
//                    return new LoginMessage("Login Failed", false);
//                }
//            } else {
//                return new LoginMessage("password Not Match", false);
//            }
//        } else {
//            return new LoginMessage("Email not exits", false);
//        }
//
//    }

    }
