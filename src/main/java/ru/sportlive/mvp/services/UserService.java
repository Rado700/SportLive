package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.repository.UserRepository;
import ru.sportlive.mvp.models.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
      return userRepository.findAll();
    }

    public Integer deposit(Integer balance){
        User user = new User();
        user.setBalance(balance);
        return balance;
    }
    public Integer withdraw (Integer balance){
        User user = new User();
        if (user.getBalance() < balance){
            user.setBalance(-balance);
        }else {
            System.out.println("сумма меньше баланса");
        }
        return user.getBalance();
    }
    public Integer getBalance(){
        User user = new User();
        return user.getBalance();
    }


}
