package ru.sportlive.mvp.services;


import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.dto.input.SportSectionDTO;
import ru.sportlive.mvp.models.*;
import ru.sportlive.mvp.repository.OrganisationRepository;
import ru.sportlive.mvp.repository.SportRepository;
import ru.sportlive.mvp.repository.SportSectionRepository;

import java.util.List;

@Service
public class SportSectionService {

    @Autowired
    SportSectionRepository sportSectionRepository;
    @Autowired
    SportRepository sportRepository;
    @Autowired
    OrganisationRepository organisationRepository;

    public SportSection getSportSection(Integer id){
        return sportSectionRepository.findById(id).orElse(null);
    }

    public SportSection addSportSection(String name, Sport sport, Organisation organisation){
        SportSection sportSection = new SportSection(name,sport,organisation);
        sportSectionRepository.save(sportSection);
        return sportSection;
    }

    public SportSection deleteSportSection(Integer id){
        SportSection sportSection = getSportSection(id);
        sportSectionRepository.delete(sportSection);
        return sportSection;
    }

    public SportSection updateSportSection(SportSection sportSection ,String name,Sport sport,Organisation organisation){
        if (sport != null) {
            sportSection.setSport(sport);
        }
        if (name != null){
            sportSection.setName(name);
        }
        if (organisation != null){
            sportSection.setOrganisation(organisation);
        }
        sportSectionRepository.save(sportSection);
        return sportSection;

    }

    public List<SportSection> getAllSportSections(){
        return sportSectionRepository.findAll();
    }

    public SportSection getSportOrganisation (Sport sport, Organisation organisation){
        for (SportSection section : sportSectionRepository.findAll()){
            if (section.getOrganisation()==organisation && section.getSport()==sport){
                return section;
            }
        }
        return null;
    }

}
