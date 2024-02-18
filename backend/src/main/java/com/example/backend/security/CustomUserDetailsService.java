package com.example.backend.security;

import com.example.backend.Entities.Board;
import com.example.backend.Entities.User;
import com.example.backend.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),mapBoardsToAuthorities(user.getBoards()));

        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection < ? extends GrantedAuthority> mapBoardsToAuthorities(Collection <Board> boards) {
        Collection < ? extends GrantedAuthority> mapRoles = boards.stream()
                .map(board -> new SimpleGrantedAuthority(board.getTitle()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}


