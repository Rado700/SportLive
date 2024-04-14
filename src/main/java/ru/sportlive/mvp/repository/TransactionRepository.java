package ru.sportlive.mvp.repository;

import ru.sportlive.mvp.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

//    Transaction addTransaction(String summa,String type);
}
