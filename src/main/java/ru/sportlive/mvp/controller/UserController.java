package ru.sportlive.mvp.controller;



import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.UsersDTO;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/")
    @Operation(summary = "Добавляет нового пользователя")
    public ResponseEntity<User>addNewUser (@RequestBody UsersDTO usersDTO, HttpSession httpSession){
        try {
            User user = userService.addUser(usersDTO.getName(), usersDTO.getSurname(), usersDTO.getHeight(),usersDTO.getWeight());
            httpSession.setAttribute("userId",user.getId());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @DeleteMapping("/")
    @Operation(summary = "Удаляет пользователя по id")
    public ResponseEntity<User>deleteUser(HttpSession httpSession ) throws Exception {
        Integer id = (Integer) httpSession.getAttribute("userId");
        if (id == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.deleteUser(id);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/all")
    @Operation(summary = "Выводит всех пользователей")
    public ResponseEntity<List<User>>getAllUser(){
        List<User>users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);

    }
    @Operation(summary = "Выводит пользователя по id")
    @GetMapping("/{id}")
    public ResponseEntity<User>getUser(@PathVariable Integer id){
        User user =userService.getUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Operation(summary = "Выводит авторизированного пользователя")
    @GetMapping("/")
    public ResponseEntity<User>getUserAuth(HttpSession httpSession){
        Integer id = (Integer) httpSession.getAttribute("userId");
        if (id == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user =userService.getUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Operation(summary = "Обновления данных у пользователя")
    @PutMapping("/")
    public ResponseEntity<User>updateUser(@RequestBody UsersDTO usersDTO,HttpSession httpSession){
        Integer id = (Integer) httpSession.getAttribute("userId");
        if (id == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUser(id);
        user = userService.updateToUser(user, usersDTO);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
