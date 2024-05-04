package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.UserPayDTO;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    UserService userService;

    @Operation(summary = "Добавить депозит на счет",description = "Ввести пользователя и сумму пополнения")
    @PostMapping("/deposit")
    public ResponseEntity<User> deposit (@RequestBody UserPayDTO userPayDTO) { // TODO: Создать DTO с user_id и sum
        User user = userService.getUser(userPayDTO.getUser_id());
        Integer deposit = userService.deposit(userPayDTO.getSum());
        user.setBalance(deposit);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @Operation(summary = "Снять со счета",description = "Снять со счета у пользователя и ввсети сумму снятия")
    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdrawBalanceUser (@RequestBody UserPayDTO userPayDTO) { // TODO: Тоже самое DTO как и для deposit
        User user = userService.getUser(userPayDTO.getUser_id());
        Integer withdraw = userService.withdraw(userPayDTO.getSum());
        user.setBalance(withdraw);
        return new ResponseEntity<>(withdraw,HttpStatus.OK);
    }
    @Operation(summary = "Выводит баланс пользователя по id")
    @GetMapping("/{userId}")
    public ResponseEntity<Integer> getUserBalance(@PathVariable Integer userId) {
        Integer userBalance = userService.getUserBalance(userId);
        return new ResponseEntity<>(userBalance, HttpStatus.OK);
    }

}
