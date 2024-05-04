package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.repository.SportRepository;

import java.util.List;

@Service
public class SportService {

    @Autowired
    SportRepository sportRepository;

    public Sport addSport(String name_sport,String description,String instruction,String equipment){
        Sport sport = new Sport(name_sport,description,instruction,equipment);
        sportRepository.save(sport);
        return sport;
    }

    public Sport getSport(Integer id){
        return sportRepository.findById(id).orElse(null);
    }

    public List<Sport> getAllSport(){
        return sportRepository.findAll();
    }
    public Sport deleteSport(Integer id){
        Sport sport = getSport(id);
        sportRepository.delete(sport);
        return sport;
    }
}
