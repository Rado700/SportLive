package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.repository.SportRepository;

@Service
public class SportService {

    @Autowired
    SportRepository sportRepository;

    public Sport addSport(String name_sport,String description,String instruction,String equipment){
        Sport sport = new Sport(name_sport,description,instruction,equipment);
        sportRepository.save(sport);
        return sport;
    }
}
