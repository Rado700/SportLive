package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Couch;

public interface CouchRepository extends JpaRepository<Couch,Integer> {
}
