package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.LoginDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.LoginService;
import ru.sportlive.mvp.services.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    CouchService couchService;

    @Operation(summary = "Регистрация абонента")
    @PostMapping("/user/registration")
    public ResponseEntity<Login>addLoginUser(@RequestBody LoginDTO loginDTO) {
        Boolean loginOccupied = loginService.isLoginOccupiedUser(loginDTO.getName());
        if (loginOccupied){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.addUsers();
        Login login = loginService.addLoginUser(loginDTO.getName(), loginDTO.getPassword(),user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Регистрация тренера")
    @PostMapping("/couch/registration")
    public ResponseEntity<Login>addLoginCouch(@RequestBody LoginDTO loginDTO) {
        Boolean loginOccupied = loginService.isLoginOccupiedCouch(loginDTO.getName());
        if (loginOccupied){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Couch couch = couchService.addCouchs();
        Login login = loginService.addLoginCouch(loginDTO.getName(),loginDTO.getPassword(),couch);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @Operation(summary = "Вход абонента")
    @PostMapping("/user/enter")
    public ResponseEntity<Login>enterUser(@RequestBody LoginDTO loginDTO) {
        Login login = loginService.enterUser(loginDTO.getName(), loginDTO.getPassword());
        if (login == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Вход тренера")
    @PostMapping("/couch/enter")
    public ResponseEntity<Login>enterCouch(@RequestBody LoginDTO loginDTO) {
        Login login = loginService.enterCouch(loginDTO.getName(), loginDTO.getPassword());
        if (login == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Вывести логин по id")
    @GetMapping("/{id}")
    public ResponseEntity<Login>getLogin(@PathVariable Integer id){
        Login login = loginService.getLogin(id);
        return new ResponseEntity<>(login,HttpStatus.OK);
    }

    @Operation(summary = "Удалить логин по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Login>deleteLogin(@PathVariable Integer id){
        Login login = loginService.deleteLogin(id);
        return new ResponseEntity<>(login , HttpStatus.OK);
    }

}
