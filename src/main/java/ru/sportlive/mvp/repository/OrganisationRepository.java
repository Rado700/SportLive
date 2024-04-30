package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation,Integer> {
}
