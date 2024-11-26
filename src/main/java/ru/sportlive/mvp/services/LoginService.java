package ru.sportlive.mvp.services;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.codec.digest.Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sportlive.mvp.dto.input.LoginDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.CouchRepository;
import ru.sportlive.mvp.repository.LoginRepository;
import ru.sportlive.mvp.repository.UserRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LoginService {

    MessageDigest md5 = MessageDigest.getInstance("MD5");

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CouchRepository couchRepository;

    public LoginService() throws NoSuchAlgorithmException {
    }

    private String hashCoder(String password) {
        try {
            Dotenv dotenv = Dotenv.load();
            String key = dotenv.get("HASHKEY");

            assert key != null;
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKeySpec);

            byte[] hmacBytes = mac.doFinal(password.getBytes());

            String hash = Base64.getEncoder().encodeToString(hmacBytes);
            return hash;

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

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
        Login login = new Login(name,hashCoder(password));
        login.setUser(user);
        return loginRepository.save(login);
    }
    public Login addLoginCouch (String name, String password,Couch couch){
        Login login = new Login(name,hashCoder(password));
        login.setCouch(couch);
        return loginRepository.save(login);

    }
    public Login enterUser (String name, String password) {
        List<Login> logins = loginRepository.findByLogin(name);
        String pass = hashCoder(password);
        for (Login login : logins) {
            if (login.getPassword().equals(pass) && login.getUser() != null) {
                return login;
            }
        }
        return null;
    }
    public Login enterCouch (String name, String password) {
        List<Login> logins = loginRepository.findByLogin(name);
        String pass = hashCoder(password);
        for (Login login : logins) {
            if (login.getPassword().equals(pass) && login.getCouch() != null) {
                return login;
            }
        }
        return null;
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


    public Login updateLogin(Login login, LoginDTO loginDTO){
        login.setLogin(loginDTO.getName());
        if (!loginDTO.getPassword().equals("")) {
            login.setPassword(loginDTO.getPassword());
        }
        loginRepository.save(login);
        return login;
    }
}
