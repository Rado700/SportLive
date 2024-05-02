package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.repository.UserRepository;
import ru.sportlive.mvp.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
      return userRepository.findAll();
    }

    public Integer deposit(Integer amount){
        User user = new User();
        user.setBalance(amount);
        return user.getBalance();
    }
    public Integer withdraw (Integer amount){
        User user = new User();
        if (user.getBalance() > amount){
            user.setBalance(-amount);
        }else {
            System.out.println("сумма меньше баланса");
        }
        return user.getBalance();
    }
    public Integer getUserBalance(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getBalance).orElse(null);
    }

    public User addUser(String name,String surname,int height,int weight){
        User user = new User(name,surname,height,weight);
        return userRepository.save(user);
    }

    public User getUser(Integer Id){
        return userRepository.findById(Id).orElse(null);

    }
    public User deleteUser(Integer id) throws Exception {
        User user = getUser(id);
        if (user == null){
            throw new Exception("нету такого пользователя");
        }
        userRepository.delete(user);
        return user;
    }



}
