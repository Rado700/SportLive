package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.dto.input.UsersDTO;
import ru.sportlive.mvp.repository.TransactionRepository;
import ru.sportlive.mvp.repository.UserRepository;
import ru.sportlive.mvp.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;


    public List<User> getAllUsers(){
      return userRepository.findAll();
    }

    public User deposit(Integer amount, User user){
        int dep =user.getBalance();
        dep += amount;
        user.setBalance(dep);
        userRepository.save(user);
        return user;
    }
    public User withdraw (Integer amount, User user){
        int balance = user.getBalance();
        balance -= amount;
        user.setBalance(balance);
        userRepository.save(user);
        return user;
    }
    public Integer getUserBalance(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getBalance).orElse(null);
    }

    public User addUser(String name,String surname,int height,int weight){
        User user = new User(name,surname,height,weight);
        return userRepository.save(user);
    }

    public User addUsers(){
        User user = new User();
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
    public User updateToUser(User user, UsersDTO usersDTO){
        user.setName(usersDTO.getName());
        user.setSurname(usersDTO.getSurname());
        user.setHeight(usersDTO.getHeight());
        user.setWeight(usersDTO.getWeight());
        userRepository.save(user);
        return user;
    }

}
