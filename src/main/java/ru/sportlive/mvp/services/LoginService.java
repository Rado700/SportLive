package ru.sportlive.mvp.services;

import io.github.cdimascio.dotenv.Dotenv;
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

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
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

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public String encrypt(String data) throws Exception {
        Dotenv dotenv = Dotenv.load();
        String key = dotenv.get("ENCRYPTION_KEY");
        String iv = dotenv.get("ENCRYPTION_IV");

        assert key != null && iv != null;
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getUrlEncoder().encodeToString(encryptedBytes);

    }

    public String decrypt(String encryptedData) throws Exception {
        Dotenv dotenv = Dotenv.load();
        String key = dotenv.get("ENCRYPTION_KEY");
        String iv = dotenv.get("ENCRYPTION_IV");

        assert key != null && iv != null;

        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    public String hashCoder(String password) {
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


    public Login getLoginByHashTgId(String hashTgId) throws NoSuchAlgorithmException {
        List<Login>getAllLogin = loginRepository.findAll();
        for (Login login:getAllLogin) {
            String telegramId = login.getTelegramId();
            if (telegramId == null){
                continue;
            }
            String hashId = hashTelegramId(telegramId);
            if (Objects.equals(hashTgId, hashId)){
                return login;
            }
        }
        return null;
    }

    public Login getLoginByTgId(String tgId) throws NoSuchAlgorithmException {
        List<Login>getAllLogin = loginRepository.findAll();
        for (Login login:getAllLogin) {
            String telegramId = login.getTelegramId();
            if (telegramId == null){
                continue;
            }
            if (Objects.equals(telegramId, tgId)){
                return login;
            }
        }
        return null;
    }
    public static String hashTelegramId(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
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
        List<Login> logins = loginRepository.findAll();
        for (Login login : logins){
            if (login.getUser() != null && login.getUser().getId() == id){
                return login;
            }
        }
        return null;
    }
    public Login getCouchLogin(Integer id){
        Optional<Couch> couch = couchRepository.findById(id);
        return couch.map(Couch::getLogin).orElse(null);
    }

    public Login addLoginUser (String name, String password,User user) {
        Login login = new Login(name.toLowerCase(),hashCoder(password));
        login.setUser(user);
        return loginRepository.save(login);
    }
    public Login addLoginCouch (String name, String password,Couch couch){
        Login login = new Login(name.toLowerCase(),hashCoder(password));
        login.setCouch(couch);
        return loginRepository.save(login);

    }
    public Login enterUser (String name, String password) {
        List<Login> logins = loginRepository.findByLogin(name.toLowerCase());
        String pass = hashCoder(password);
        for (Login login : logins) {
            if (login.getPassword().equals(pass) && login.getUser() != null) {
                return login;
            }
        }
        return null;
    }
    public Login enterCouch (String name, String password) {
        List<Login> logins = loginRepository.findByLogin(name.toLowerCase());
        String pass = hashCoder(password);
        for (Login login : logins) {
            if (login.getPassword().equals(pass) && login.getCouch() != null) {
                return login;
            }
        }
        return null;
    }

    public void setTgForUserCouch(Integer loginId, String telegramId){
        Login login = getLogin(loginId);
        if (login != null){
                login.setTelegramId(telegramId);
        }
    }

    public String getTgForCouch(Integer loginId){
        Login login = getLogin(loginId);
        return login.getTelegramId();
    }

    public String getTgForUser(Integer userId){
        Login login = getLogin(userId);
        return login.getTelegramId();
    }
    public Boolean isLoginOccupiedCouch(String login){
        List<Login> byLogin = loginRepository.findByLogin(login.toLowerCase());
        for (Login logins:byLogin) {
            if (logins.getCouch() != null){
                return true;
            }
        }
        return false;
    }

    public Boolean isLoginOccupiedUser(String login) {
        List<Login> byLogin = loginRepository.findByLogin(login.toLowerCase());
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
