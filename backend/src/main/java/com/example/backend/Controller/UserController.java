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
    // handler method to handle home page request
//    @GetMapping("/")
//    public String home(){
//        return "HomePage";
//    }

    // handler method to handle user registration form request
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model){
//        // create model object to store form data
//        UserDto user = new UserDto();
//        model.addAttribute("user", user);
//        return "register";
//    }

    // handler method to handle login request
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }

    @PostMapping("/login") // Change the mapping for login functionality
    public String login(@Valid @ModelAttribute("user") @RequestBody UserDto userDto,
                             BindingResult result,
                             Model model) {
        String email = userDto.getEmail();
        String password=userDto.getPassword();
        User existingUser = userService.findUserByEmail(email);

        if (existingUser != null && existingUser.getPassword().equals(password)) {
            return userDto.getId().toString(); // Redirect to the users page if password matches
        } else {
            return null; // Redirect back to login if password doesn't match or user doesn't exist
        }
    }
//@PostMapping("/login")
//public String login_user(@RequestParam("username") String username, @RequestParam("password") String password,
//                         HttpSession session, ModelMap modelMap)
//{
//
//    User auser=userRepository.findByUsernamePassword(username, password);
//
//    if(auser!=null)
//    {
//        String uname=auser.getEmail();
//        String upass=auser.getPassword();
//
//        if(username.equalsIgnoreCase(uname) && password.equalsIgnoreCase(upass))
//        {
//            session.setAttribute("username",username);
//            return "dummy";
//        }
//        else
//        {
//            modelMap.put("error", "Invalid Account");
//            return "login";
//        }
//    }
//    else
//    {
//        modelMap.put("error", "Invalid Account");
//        return "login";
//    }
//
//}
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
