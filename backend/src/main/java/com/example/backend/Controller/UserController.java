package com.example.backend.Controller;

import com.example.backend.DTO.BoardDTO;
import com.example.backend.DTO.TaskCardDTO;
import com.example.backend.DTO.UserDto;
import com.example.backend.Entities.Board;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Entities.User;
import com.example.backend.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public UserController(UserService userService){
        this.userService=userService;
    }



    @PostMapping("/login") // Change the mapping for login functionality
    public ResponseEntity<String> login(@Valid @RequestBody UserDto userDto,
                                        BindingResult result,
                                        Model model) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        User existingUser = userService.findUserByEmail(email);

        System.out.println(email);
        System.out.println(password);
        if (existingUser != null && existingUser.getPassword().equals(password)) {
            // Redirect to the users page if password matches
            // Assuming you want to return the user's ID
            Integer userId = existingUser.getId();
            // You can also return other details along with the ID if needed
            return new ResponseEntity<>("success, userId: " + userId, HttpStatus.OK);
        } else {
            // Redirect back to login if password doesn't match or user doesn't exist
            return new ResponseEntity<>("failure", HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/users")
    public ResponseEntity<?> users(Model model){
//        List<UserDto> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "users";
        try{
            return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
        }catch (Exception e){
            return errorResponse();
        }
    }
    // handler method to handle user registration form submit request
//    @PostMapping("/register")
//    public ResponseEntity<?> registration(@Valid  @RequestBody  UserDto userDto,
//                               BindingResult result,
//                               Model model){
//        User existingUser = userService.findUserByEmail(userDto.getEmail());
//
//        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
//            result.rejectValue("email", null,
//                    "There is already an account registered with the same email");
//        }
//
//        if(result.hasErrors()){
//            model.addAttribute("user", userDto);
//            return errorResponse();
//        }
//
//        userService.saveUser(userDto);
//       // return userDto.getId().toString();
//        return new ResponseEntity<>(
//                userDto,
//                HttpStatus.OK);
//    }
    @PostMapping("/register")
    public ResponseEntity<?> registration(@Valid @RequestBody UserDto userDto,
                                          BindingResult result,
                                          Model model) {
        String email = userDto.getEmail();
        User existingUser = userService.findUserByEmail(email);

        if (existingUser != null) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(userDto);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @GetMapping("/dashboard/{userId}/")
    public ResponseEntity<?> getAllBoardsInUser(@PathVariable Integer userId){
        try {
            Optional<User> optionalUser=userService.getUserById(userId);
            if(optionalUser.isPresent()){
                return new ResponseEntity<>(
                        optionalUser.get().getBoards(),
                        HttpStatus.OK);
            }else {
                return noUserFoundResponse(userId);
            }
        }catch (Exception e){
            return errorResponse();
        }
    }

    @PostMapping("/dashboard/{userId}/")
    public ResponseEntity<?> createBoardAssignedToUser(@PathVariable Integer userId,@RequestBody BoardDTO boardDTO){
       try {
           return new ResponseEntity<>(
                   userService.addNewBoardToUser(userId, boardDTO),HttpStatus.CREATED
           );
       }catch (Exception e)
       {
           return errorResponse();
       }
    }




    private ResponseEntity<String> errorResponse() {
        return  new ResponseEntity<>("Something went wrong : (",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String>noUserFoundResponse(Integer id){
        return new ResponseEntity<>("No User found with id: " + id,HttpStatus.NOT_FOUND);
    }
}
