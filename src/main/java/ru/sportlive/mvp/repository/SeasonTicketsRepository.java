package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.SeasonTicket;

import java.util.List;
import java.util.UUID;

public interface SeasonTicketsRepository extends JpaRepository<SeasonTicket,Integer> {
    List<SeasonTicket> findAllByUuid(UUID uuid);

}
