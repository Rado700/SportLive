package ru.sportlive.mvp.services;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.repository.OrganisationRepository;

import java.util.List;

@Service
public class OrganisationService {

    @Autowired
    OrganisationRepository organisationRepository;

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
}
