package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.UserPayDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.models.Transaction;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.LoginService;
import ru.sportlive.mvp.services.TransactionService;
import ru.sportlive.mvp.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    CouchService couchService;
    @Autowired
    LoginService loginService;

    @Operation(summary = "Добавить депозит на счет для пользователя",description = "Ввести сумму пополнения")
    @PostMapping("/user/deposit/")
    public ResponseEntity<Object> deposit (@RequestBody UserPayDTO userPayDTO,HttpSession httpSession) {
        Integer user_id =(Integer) httpSession.getAttribute("userId");
        if (user_id == null){
            return new ResponseEntity<>("Пользователь не авторизован",HttpStatus.UNAUTHORIZED);
        }


        User user = userService.getUser(user_id);
        user = userService.deposit(userPayDTO.getSum().doubleValue(), user);
        Integer login_id = (Integer) httpSession.getAttribute("loginUserId");
        Login login = loginService.getLogin(login_id);
        transactionService.addTransaction(login,userPayDTO.getSum(),"deposit");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Operation(summary = "Снять со счета",description = "Снять со счета у пользователя, ввести сумму снятия")
    @PostMapping("/user/withdraw/")
    public ResponseEntity<Object> withdrawBalanceUser (@RequestBody UserPayDTO userPayDTO,HttpSession httpSession) {
        Integer user_id = (Integer)httpSession.getAttribute("userId");
        if (user_id == null){
            return new ResponseEntity<>("Пользователь не авторизован",HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUser(user_id);
        if (userPayDTO.getSum() > user.getBalance()){
            return new ResponseEntity<>("Не достаточно средств на балансе",HttpStatus.PAYMENT_REQUIRED);
        }
        user = userService.withdraw(userPayDTO.getSum(),user);
        transactionService.addTransaction(user.getLogin(),userPayDTO.getSum(),"withdraw");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @Operation(summary = "Выводит баланс пользователя по id")
    @GetMapping("/{userId}")
    public ResponseEntity<Integer> getUserBalance(@PathVariable Integer userId) {
        Integer userBalance = userService.getUserBalance(userId);
        return new ResponseEntity<>(userBalance, HttpStatus.OK);
    }
    @Operation(summary = "Выводит баланс пользователя")
    @GetMapping("/balanceUser/")
    public ResponseEntity<Integer> getUserBalance(HttpSession httpSession) {
        Integer user_id = (Integer) httpSession.getAttribute("userId");
        Integer userBalance = userService.getUserBalance(user_id);
        return new ResponseEntity<>(userBalance, HttpStatus.OK);
    }

    @Operation(summary = "Выводит транзакцию по id")
    @GetMapping("/{id}")
    public ResponseEntity<Transaction>getTransaction(@PathVariable Integer id){
        Transaction transaction = transactionService.getTransaction(id);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @Operation(summary = "Выводит транзакцию пользователя")
    @GetMapping("/transactionUser/")
    public ResponseEntity<Transaction>getTransaction(HttpSession httpSession){
        Integer id = (Integer) httpSession.getAttribute("userId");
        Transaction transaction = transactionService.getTransaction(id);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @Operation(summary = "Удаляет транзакцию по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction>deleteTransaction(@PathVariable Integer id){
        Transaction transaction = transactionService.deleteTransaction(id);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @Operation(summary = "Перевод средств от user к couch")
    @PostMapping("/transfer/user/couch/{couch_id}")
    public ResponseEntity<Object>transferUserCouch(@RequestBody UserPayDTO userPayDTO, @PathVariable Integer couch_id,HttpSession httpSession){
        Integer user_id = (Integer)httpSession.getAttribute("userId");
        if (user_id == null){
            return new ResponseEntity<>("Пользователь не авторизован",HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUser(user_id);
        if (userPayDTO.getSum() > user.getBalance()){
            return new ResponseEntity<>("Не достаточно средств на балансе",HttpStatus.PAYMENT_REQUIRED);
        }
        user = userService.withdraw(userPayDTO.getSum(),user);
        Couch couch = couchService.getCouch(couch_id);
        if (couch == null){
            return new ResponseEntity<>("Выберите тренера",HttpStatus.NOT_FOUND);
        }
        couch = couchService.deposit(userPayDTO.getSum(),couch);
        Transaction transaction1 = transactionService.addTransaction(user.getLogin(),userPayDTO.getSum(),"Перевод средств тренеру "+couch.getName());
        Transaction transaction2 = transactionService.addTransaction(couch.getLogin(),userPayDTO.getSum(),"Пополнения средств от "+user.getName());
        List<Transaction>getTransaction = new ArrayList<>();
        getTransaction.add(transaction1);
        getTransaction.add(transaction2);
        return new ResponseEntity<>(getTransaction,HttpStatus.OK);
    }

    @Operation(summary = "Добавить на счет для тренера",description = "Внести сумму пополнения")
    @PostMapping("/couch/deposit/")
    public ResponseEntity<Object>addBalanceCouch(@RequestBody UserPayDTO userPayDTO, HttpSession httpSession){
        Integer couch_id = (Integer) httpSession.getAttribute("couchId");
        if (couch_id == null){
            return new ResponseEntity<>("Нужно авторизоваться",HttpStatus.UNAUTHORIZED);
        }
        Couch couch = couchService.getCouch(couch_id);
        couch = couchService.deposit(userPayDTO.getSum(),couch);
        transactionService.addTransaction(couch.getLogin(),userPayDTO.getSum(),"deposit");
        return new ResponseEntity<>(couch,HttpStatus.OK);
    }

    @Operation(summary = "Снять со счета у тренера")
    @PostMapping("/couch/withdraw/")
    public ResponseEntity<Object>withdrawBalanceCouch(@RequestBody UserPayDTO userPayDTO,HttpSession httpSession){
        Integer couch_id = (Integer) httpSession.getAttribute("couchId");
        if (couch_id == null){
            return new ResponseEntity<>("Нужно авторизоваться",HttpStatus.UNAUTHORIZED);
        }
        Couch couch = couchService.getCouch(couch_id);
        if (userPayDTO.getSum()>couch.getBalance()){
            return new ResponseEntity<>("Не достаточно средств на балансе",HttpStatus.PAYMENT_REQUIRED);
        }
        couch = couchService.withdraw(userPayDTO.getSum(),couch);
        Transaction transaction = transactionService.addTransaction(couch.getLogin(),userPayDTO.getSum(),"withdraw");
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @Operation(summary = "Перевод средств от couch к user")
    @PostMapping("/transfer/couch/user/{user_id}")
    public ResponseEntity<Object>transferCouchUser(@RequestBody UserPayDTO userPayDTO,@PathVariable Integer user_id,HttpSession httpSession){
        Integer couch_id = (Integer)httpSession.getAttribute("couchId");
        if (couch_id == null){
            return new ResponseEntity<>("Тренер не авторизован",HttpStatus.UNAUTHORIZED);
        }
        Couch couch = couchService.getCouch(couch_id);
        if (userPayDTO.getSum() > couch.getBalance()){
            return new ResponseEntity<>("Не достаточно средств на балансе",HttpStatus.PAYMENT_REQUIRED);
        }
        couch = couchService.withdraw(userPayDTO.getSum(),couch);
        User user = userService.getUser(user_id);
        if (user == null){
            return new ResponseEntity<>("Выберите пользователя",HttpStatus.NOT_FOUND);
        }
        user = userService.deposit(userPayDTO.getSum().doubleValue(),user);
        Transaction transaction1 = transactionService.addTransaction(couch.getLogin(),userPayDTO.getSum(),"Пополнения средств от "+couch.getName());
        Transaction transaction2 = transactionService.addTransaction(user.getLogin(),userPayDTO.getSum(),"Перевод средств пользователю"+user.getName());
        List<Transaction>getTransaction = new ArrayList<>();
        getTransaction.add(transaction1);
        getTransaction.add(transaction2);
        return new ResponseEntity<>(getTransaction,HttpStatus.OK);
    }
}
