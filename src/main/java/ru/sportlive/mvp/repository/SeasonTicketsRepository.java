package ru.sportlive.mvp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sportlive.mvp.models.SeasonTicket;

public interface SeasonTicketsRepository extends JpaRepository<SeasonTicket,Integer> {
    List<SeasonTicket> findAllByUuid(UUID uuid);

}
