package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.models.Transaction;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.TransactionRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Transactional
@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public Transaction getTransaction(Integer id){
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction addTransaction(Login login, Double summa, String type) {
        LocalDateTime date = LocalDateTime.now();
        Transaction transaction = new Transaction(summa, type, date,login);
        transactionRepository.save(transaction);
        return transaction;
    }

    public Transaction deleteTransaction(Integer id){
        Transaction transaction = getTransaction(id);
        transactionRepository.delete(transaction);
        return transaction;
    }
}
