package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.dto.input.LoginDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.CouchRepository;
import ru.sportlive.mvp.repository.LoginRepository;
import ru.sportlive.mvp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CouchRepository couchRepository;

    public Login getLogin(Integer id){
        return loginRepository.findById(id).orElse(null);
    }
    public Login deleteLogin(Integer id){
        Login login = getLogin(id);
        loginRepository.delete(login);
        return login;
    }
    public Login getUserLogin(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getLogin).orElse(null);
    }

    public Login getCouchLogin(Integer id){
        Optional<Couch> couch = couchRepository.findById(id);
        return couch.map(Couch::getLogin).orElse(null);
    }
    public Login addLoginUser (String name, String password,User user) {
        Login login = new Login(name,password);
        login.setUser(user);
        return loginRepository.save(login);
    }
    public Boolean isLoginOccupiedCouch(String login){
        List<Login> byLogin = loginRepository.findByLogin(login);
        for (Login logins:byLogin) {
            if (logins.getCouch() != null){
                return true;
            }
        }
        return false;

    }
    public Boolean isLoginOccupiedUser(String login) {
        List<Login> byLogin = loginRepository.findByLogin(login);
        for (Login logins : byLogin) {
            if (logins.getUser() != null) {
                return true;
            }
        }
        return false;
    }

    public Login addLoginCouch (String name, String password,Couch couch){
        Login login = new Login(name,password);
        login.setCouch(couch);
        return loginRepository.save(login);

    }
    public Login enterUser (String name, String password) {
        List<Login> logins = loginRepository.findByLogin(name);
        for (Login login : logins) {
            if (login.getPassword().equals(password) && login.getUser() != null) {
                return login;
            }
        }
        return null;
    }
    public Login enterCouch (String name, String password) {
        List<Login> logins = loginRepository.findByLogin(name);
        for (Login login : logins) {
            if (login.getPassword().equals(password) && login.getCouch() != null) {
                return login;
            }
        }
        return null;
    }

    public Login updateLogin(Login login, LoginDTO loginDTO){
        login.setLogin(loginDTO.getName());
        if (!loginDTO.getPassword().equals("")) {
            login.setPassword(loginDTO.getPassword());
        }
        loginRepository.save(login);
        return login;
    }
}
