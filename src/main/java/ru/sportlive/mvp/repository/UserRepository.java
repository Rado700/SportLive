package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}
