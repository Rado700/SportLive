package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.UserPayDTO;
import ru.sportlive.mvp.models.Transaction;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.TransactionService;
import ru.sportlive.mvp.services.UserService;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;

    @Operation(summary = "Добавить депозит на счет",description = "Ввести пользователя и сумму пополнения")
    @PostMapping("/deposit")
    public ResponseEntity<User> deposit (@RequestBody UserPayDTO userPayDTO) {
        User user = userService.getUser(userPayDTO.getUser_id());
        user = userService.deposit(userPayDTO.getSum(), user);
        transactionService.addTransaction(user,userPayDTO.getSum(),"deposit");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @Operation(summary = "Снять со счета",description = "Снять со счета у пользователя и ввсети сумму снятия")
    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdrawBalanceUser (@RequestBody UserPayDTO userPayDTO) {
        User user = userService.getUser(userPayDTO.getUser_id());
        user = userService.withdraw(userPayDTO.getSum(),user);
        transactionService.addTransaction(user,userPayDTO.getSum(),"withdraw");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @Operation(summary = "Выводит баланс пользователя по id")
    @GetMapping("/{userId}")
    public ResponseEntity<Integer> getUserBalance(@PathVariable Integer userId) {
        Integer userBalance = userService.getUserBalance(userId);
        return new ResponseEntity<>(userBalance, HttpStatus.OK);
    }

    @Operation(summary = "Выводит транзакцию по id")
    @GetMapping("/{id}")
    public ResponseEntity<Transaction>getTransaction(@PathVariable Integer id){
        Transaction transaction = transactionService.getTransaction(id);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }
    @Operation(summary = "Удаляет транзакцию по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction>deleteTransaction(@PathVariable Integer id){
        Transaction transaction = transactionService.deleteTransaction(id);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

}
