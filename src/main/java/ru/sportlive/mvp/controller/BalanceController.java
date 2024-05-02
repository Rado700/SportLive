package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    UserService userService;

    @PostMapping("/deposit")
    public ResponseEntity<Object> deposit (@PathVariable Integer id,@PathVariable Integer sum) { // TODO: Создать DTO с user_id и sum
        User user = userService.getUser(id);
        Integer deposit = userService.deposit(sum);
        user.setBalance(deposit);
        return new ResponseEntity<>(deposit,HttpStatus.OK);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdrawBalanceUser (@PathVariable Integer id,@PathVariable Integer sum) { // TODO: Тоже самое DTO как и для deposit
        User user = userService.getUser(id);
        Integer withdraw = userService.withdraw(sum);
        user.setBalance(withdraw);
        return new ResponseEntity<>(withdraw,HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Integer> getUserBalance(@PathVariable Integer userId) {
        Integer userBalance = userService.getUserBalance(userId);
        return new ResponseEntity<>(userBalance, HttpStatus.OK);
    }

}
