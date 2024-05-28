package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Login;

import java.util.List;

public interface LoginRepository extends JpaRepository<Login,Integer> {

    List<Login> findByLogin (String name);
}
