package ru.sportlive.mvp.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.SportDTO;
import ru.sportlive.mvp.dto.UsersDTO;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.SportService;
import ru.sportlive.mvp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<User>addNewUser (@RequestBody UsersDTO usersDTO){
        try {
            User user = userService.addUser(usersDTO.getName(), usersDTO.getSurname(), usersDTO.getHeight(),usersDTO.getWeight());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User>deleteUser(@PathVariable Integer id) throws Exception {
        User user = userService.deleteUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<User>>getAllUser(){
        List<User>users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<User>getUser(@PathVariable Integer id){
        User user =userService.getUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
