package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Sport;

public interface SportRepository extends JpaRepository<Sport,Integer> {
}
