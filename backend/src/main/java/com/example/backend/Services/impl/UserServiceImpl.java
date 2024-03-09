package com.example.backend.Services.impl;

import com.example.backend.DTO.BoardDTO;
import com.example.backend.DTO.UserDto;
import com.example.backend.Entities.Board;
import com.example.backend.Entities.User;
import com.example.backend.Repository.BoardRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Services.UserService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    @Transactional
    public User verifyUser(User s) {
        String email = s.getEmail();
        User s1 = userRepository.findByEmail(email);

        if (s1.getPassword().equals(s.getPassword())) {
            return s1;

        } else
            return null;
    }

    @Override
    @Transactional
    public void deleteUser(String email) {

    }

    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        User user=new User();
        user.setName(userDto.getFirstName() + " "+userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
    }
    @Override
    @Transactional
    public User findUserByEmail(String email) {
               return userRepository.findByEmail(email);
    }
    @Override
    @Transactional
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional
    public User addNewBoardToUser(Long userId, BoardDTO boardDTO) {
            User user=userRepository.findById(userId).get();
            user.addBoard(convertDTOToBoard(boardDTO));
            return userRepository.save(user);
    }

    private Board convertDTOToBoard(BoardDTO boardDTO) {
        Board board=new Board();
        board.setId(boardDTO.getId());
        board.setTitle(boardDTO.getTitle());
        board.setDescription(boardDTO.getDescription());
        return board;
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setId(user.getId());
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
