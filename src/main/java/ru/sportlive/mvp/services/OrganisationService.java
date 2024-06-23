package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.dto.input.OrganisationDTO;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.models.SportSection;
import ru.sportlive.mvp.repository.OrganisationRepository;
import ru.sportlive.mvp.repository.SportOrganisationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganisationService {

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    SportOrganisationRepository sportOrganisationRepository;

    public Organisation getOrganisation(Integer id) {
        return organisationRepository.findById(id).orElse(null);
    }

    public Organisation addOrganisation(String name,String description){
        Organisation organisation = new Organisation(name,description);
        organisationRepository.save(organisation);
        return organisation;
    }

    public List<Organisation> getAllOrganisations(){
        return organisationRepository.findAll();
    }
    public Organisation deleteOrganisation(Integer id){
        Organisation organisation = getOrganisation(id);
        organisationRepository.delete(organisation);
        return organisation;
    }

    public Organisation updateToOrganisation(Organisation organisation, OrganisationDTO organisationDTO){
        organisation.setName(organisation.getName());
        organisation.setDescription(organisationDTO.getDescription());
        organisationRepository.save(organisation);
        return organisation;
    }

    public Organisation addSportForOrganisation(Sport sport, Organisation organisation){
        SportSection sportSection = new SportSection();
        sportSection.setOrganisation(organisation);
        sportSection.setSport(sport);
        sportOrganisationRepository.save(sportSection);
        return organisation;
    }

    public List<Sport>getAllSportForOrganisation(Organisation organisation){
        List<SportSection> sportSection = organisation.getSportSection();
        return sportSection.stream().map(SportSection::getSport).collect(Collectors.toList());
    }


}
