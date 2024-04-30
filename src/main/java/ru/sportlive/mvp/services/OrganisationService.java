package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.repository.OrganisationRepository;

@Service
public class OrganisationService {

    @Autowired
    OrganisationRepository organisationRepository;

    public Organisation getOrganisation(Integer id) {
        return organisationRepository.findById(id).orElse(null);
    }
}
