package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.UserRepository;
import ru.sportlive.mvp.services.UserService;

import java.util.List;

@RestController
public class BalanceController {

    @Autowired
    UserService userService;

    @PostMapping("/addBalance")
    public Integer deposit (int balance) {
        return userService.deposit(balance);
    }
    @PostMapping("/balance")
    public Integer withdraw (int balance) {
        return userService.withdraw(balance);
    }
    @GetMapping("/balance")
    public Integer getBalance(){
        return userService.getBalance();
    }

}
