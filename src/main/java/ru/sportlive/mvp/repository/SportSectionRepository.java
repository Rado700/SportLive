package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.SportSection;

public interface SportSectionRepository extends JpaRepository<SportSection,Integer> {
}
