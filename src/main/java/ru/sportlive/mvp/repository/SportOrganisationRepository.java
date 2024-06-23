package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.SportSection;

public interface SportOrganisationRepository extends JpaRepository<SportSection,Integer> {
}
