package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Transaction;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.TransactionRepository;

import java.sql.Timestamp;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public Transaction getTransaction(Integer id){
        return transactionRepository.findById(id).orElse(null);
    }

    public void addTransaction(User user, Integer summa, String type) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction(summa, type, date, user);
        transactionRepository.save(transaction);
    }

    public Transaction deleteTransaction(Integer id){
        Transaction transaction = getTransaction(id);
        transactionRepository.delete(transaction);
        return transaction;
    }
}
