package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.dto.input.SportDTO;
import ru.sportlive.mvp.models.*;
import ru.sportlive.mvp.repository.SportRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Sport updateToSport(Sport sport, SportDTO sportDTO){
        sport.setName_sport(sportDTO.getName_sport());
        sport.setDescription(sportDTO.getDescription());
        sport.setInstruction(sportDTO.getInstruction());
        sport.setEquipment(sportDTO.getEquipment());
        sportRepository.save(sport);
        return sport;
    }

    public List<Sport>getAllSportForOrganisation(Organisation organisation){
        List<SportSection> sport = organisation.getSportSection();
        return sport.stream().map(SportSection::getSport).collect(Collectors.toList());
    }

//    public List<Sport>getAllSportForCouch(Couch couch){
//        List<CouchSport>getAllCouch = couch.getCouchSport();
//        return getAllCouch.stream().map(CouchSport::getSport).collect(Collectors.toList());
//    }
    public List<Couch> getAllCouchForSportOrganisation(Sport sport, Organisation organisation){
        List<SportSection>getAllSportSections = sport.getSportSection();
        Set<Couch>couches = new HashSet<>();
        for (SportSection section : getAllSportSections){
            if (section.getOrganisation() == organisation) {
                couches.addAll(section.getCouches());
            }
        }
        return couches.stream().toList();
    }
}
