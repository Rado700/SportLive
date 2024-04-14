package ru.sportlive.mvp.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.UserService;

import java.util.List;

@RestController
public class UserController {

    UserService userService;



    public void addBalance (int balance){
        Integer balances = userService.deposit(balance);
    }

}
