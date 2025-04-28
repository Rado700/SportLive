package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Notes;
import ru.sportlive.mvp.models.User;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
    List<Notes> findAllByUserOrderByDateTimeDesc(User user);
    List<Notes> findAllByCouchOrderByDateTimeDesc(Couch couch);
}
