package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.AuthTgDTO;
import ru.sportlive.mvp.dto.input.LoginDTO;
import ru.sportlive.mvp.dto.output.LoginInfoDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.LoginService;
import ru.sportlive.mvp.services.UserService;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    CouchService couchService;

//    @Operation(summary = "Выбор Организаций")
//    @PostMapping("/organisationType")
//    public ResponseEntity<Login>enterCouch(@RequestBody LoginDTO loginDTO,HttpSession httpSession) {
//        Login login = loginService.enterCouch(loginDTO.getName(), loginDTO.getPassword());
//        if (login == null){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        httpSession.setAttribute("couchId",login.getCouch().getId());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @Operation(summary = "Выбор Тренера")
//    @PostMapping("/couchEnter")
//    public ResponseEntity<Login>enterCouch(@RequestBody LoginDTO loginDTO,HttpSession httpSession) {
//        Login login = loginService.enterCouch(loginDTO.getName(), loginDTO.getPassword());
//        if (login == null){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        httpSession.setAttribute("couchId",login.getCouch().getId());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/user/registration/")
    public ResponseEntity<Login> addLoginUser(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        Boolean loginOccupied = loginService.isLoginOccupiedUser(loginDTO.getName());
        if (loginOccupied) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.addUsers();
        Login login = loginService.addLoginUser(loginDTO.getName(), loginDTO.getPassword(), user);
        httpSession.setAttribute("userId", user.getId());
        httpSession.setAttribute("loginUserId", login.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Регистрация тренера")
    @PostMapping("/couch/registration/")
    public ResponseEntity<Login> addLoginCouch(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        Boolean loginOccupied = loginService.isLoginOccupiedCouch(loginDTO.getName());
        if (loginOccupied) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Couch couch = couchService.addCouches();
        Login login = loginService.addLoginCouch(loginDTO.getName(), loginDTO.getPassword(), couch);
        httpSession.setAttribute("couchId", couch.getId());
        httpSession.setAttribute("loginCouchId", login.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Вход пользователя")
    @PostMapping("/user/enter/")
    public ResponseEntity<Login> enterUser(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        Login login = loginService.enterUser(loginDTO.getName(), loginDTO.getPassword());
        if (login == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        httpSession.setAttribute("userId", login.getUser().getId());
        httpSession.setAttribute("loginUserId", login.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Вход тренера")
    @PostMapping("/couch/enter/")
    public ResponseEntity<Login> enterCouch(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        Login login = loginService.enterCouch(loginDTO.getName(), loginDTO.getPassword());
        if (login == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        httpSession.setAttribute("couchId", login.getCouch().getId());
        httpSession.setAttribute("loginCouchId", login.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Обновить Login у тренера")
    @PutMapping("/couch/")
    public ResponseEntity<Login> updateCouchLogin(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        Integer couch_id = (Integer) httpSession.getAttribute("loginCouchId");
        Login login = loginService.getLogin(couch_id);

        login = loginService.updateLogin(login, loginDTO);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @Operation(summary = "Обновить Login у пользователя")
    @PutMapping("/user/")
    public ResponseEntity<Object> updateUserLogin(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
//        Integer user_id = (Integer) httpSession.getAttribute("userId");
        Integer login_id = (Integer) httpSession.getAttribute("loginUserId");
        Login login = loginService.getLogin(login_id);
        Boolean isOccupiedLogin = loginService.isLoginOccupiedUser(loginDTO.getName());
        if (isOccupiedLogin && !login.getLogin().equals(loginDTO.getName())) {
            return new ResponseEntity<>("Такой никнейм уже занят", HttpStatus.BAD_REQUEST);
        }
        login = loginService.updateLogin(login, loginDTO);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @Operation(summary = "Вывести логин по id")
    @GetMapping("/{id}")
    public ResponseEntity<Login> getLogin(@PathVariable Integer id) {
        Login login = loginService.getLogin(id);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @Operation(summary = "Вывести логин ")
    @GetMapping("/")
    public ResponseEntity<Object> getLogin(HttpSession httpSession) {
        Integer loginUserId = (Integer) httpSession.getAttribute("loginUserId");
        Integer loginCouchId = (Integer) httpSession.getAttribute("loginCouchId");
        if (loginCouchId == null && loginUserId == null) {
            return new ResponseEntity<>("Не авторизирован", HttpStatus.UNAUTHORIZED);
        }
        Login login = loginService.getLogin(loginUserId);
        if (login == null) {
            login = loginService.getLogin(loginCouchId);
        }
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @Operation(summary = "Выход из тренера")
    @GetMapping("/couch/exit")
    public ResponseEntity<Couch> getExitCouch(HttpSession httpSession) {
        httpSession.removeAttribute("couchId");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Выход из пользователя")
    @GetMapping("/user/exit")
    public ResponseEntity<User> getExitUser(HttpSession httpSession) {
        httpSession.removeAttribute("userId");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить логин по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Login> deleteLogin(@PathVariable Integer id) {
        Login login = loginService.deleteLogin(id);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @Operation(summary = "Хэширование логин id")
    @GetMapping("/hashId/")
    public ResponseEntity<String> hashId(HttpSession httpSession) throws Exception {
        Integer loginUserId = (Integer) httpSession.getAttribute("loginUserId");
        Integer loginCouchId = (Integer) httpSession.getAttribute("loginCouchId");
        String hashId = null;
        if (loginCouchId == null && loginUserId == null) {
            return new ResponseEntity<>("Не авторизирован", HttpStatus.UNAUTHORIZED);
        }
        if (loginCouchId != null) {
            hashId = String.valueOf(loginCouchId);
            hashId = loginService.encrypt(hashId);

        }
        if (loginUserId != null) {
            hashId = String.valueOf(loginUserId);
            hashId = loginService.encrypt(hashId);
        }
        return new ResponseEntity<>(hashId, HttpStatus.OK);
    }

    @Operation(summary = "Хэширование Телеграмм id")
    @PostMapping("/tg/enter/")
    public ResponseEntity<String> getTgHashId(@RequestBody AuthTgDTO authTgDTO, HttpSession httpSession) throws NoSuchAlgorithmException {
        String hastTgId = authTgDTO.getTgId();

        Login login = loginService.getLoginByHashTgId(hastTgId);
        if (login == null) {
            return new ResponseEntity<>("/",HttpStatus.UNAUTHORIZED);
        }
        if (login.getUser() != null) {
            httpSession.setAttribute("userId", login.getUser().getId());
            httpSession.setAttribute("loginUserId", login.getId());
            return new ResponseEntity<>("/account",HttpStatus.OK);
        }
        if (login.getCouch() != null){
            httpSession.setAttribute("couchId", login.getCouch().getId());
            httpSession.setAttribute("loginCouchId", login.getId());
            return new ResponseEntity<>("/couches",HttpStatus.OK);
        }
        return new ResponseEntity<>("/", HttpStatus.OK);
    }

    @Operation(summary = "Принимает Логин и Телеграмм id для инициализаций пользователя")
    @PostMapping("/auth/tg/")
    public ResponseEntity<Object> getLoginTgId(@RequestBody AuthTgDTO authTgDTO) throws Exception {
        String decryptHashIdLogin = null;
        decryptHashIdLogin = loginService.decrypt(authTgDTO.getLoginId());
        Login login = loginService.getLogin(Integer.valueOf(decryptHashIdLogin));
        loginService.setTgForUserCouch(login.getId(), authTgDTO.getTgId());
        return new ResponseEntity<>(decryptHashIdLogin, HttpStatus.OK);
    }

    @Operation(summary = "Получить по TgId пользователя и тренера")
    @GetMapping("/tgId/{tgId}")
    public ResponseEntity<LoginInfoDTO>getCouchUserByTgId(@PathVariable String tgId) throws NoSuchAlgorithmException {
        Login login = loginService.getLoginByHashTgId(tgId);
        if (login == null){
            return null;
        }
        return new ResponseEntity<>(login.getLoginInfo(),HttpStatus.OK);
//        User user = login.getUser();
//        Couch couch = login.getCouch();
//        if (user != null){
//            return new ResponseEntity<>(user,HttpStatus.OK);
//        } else if (couch != null) {
//            return new ResponseEntity<>(couch,HttpStatus.OK);
//        }else {
//            return null;
//        }
    }

}
