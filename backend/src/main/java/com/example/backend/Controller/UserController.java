package com.example.backend.Controller;

import com.example.backend.DTO.UserDto;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Entities.User;
import com.example.backend.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/login") // Change the mapping for login functionality
    public String login(@Valid @ModelAttribute("user") @RequestBody UserDto userDto,
                             BindingResult result,
                             Model model) {
        String email = userDto.getEmail();
        String password=userDto.getPassword();
        User existingUser = userService.findUserByEmail(email);

        if (existingUser != null && existingUser.getPassword().equals(password)) {
            return "success"; // Redirect to the users page if password matches
        } else {
            return "failure"; // Redirect back to login if password doesn't match or user doesn't exist
        }
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    // handler method to handle user registration form submit request
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") @RequestBody  UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return null;
        }

        userService.saveUser(userDto);
        return userDto.getId().toString();
    }

}
