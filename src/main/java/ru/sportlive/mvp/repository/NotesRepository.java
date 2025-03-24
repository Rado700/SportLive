package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Notes;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
}
