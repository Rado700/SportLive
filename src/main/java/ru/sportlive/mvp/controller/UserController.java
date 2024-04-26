package ru.sportlive.mvp.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.UsersDTO;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.UserService;

import java.awt.geom.QuadCurve2D;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/addUser")
    public ResponseEntity<User>addNewUser (@RequestBody UsersDTO usersDTO){

        try {
            User user = userService.addUser(usersDTO.getName(), usersDTO.getSurname(), usersDTO.getHeight(),usersDTO.getWeight());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @DeleteMapping("/user/{Id}")
    public ResponseEntity<User>deleteUser(@PathVariable Integer Id) throws Exception {
        User user = userService.deleteUser(Id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>>getAllUser(){
        List<User>users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);

    }

}
