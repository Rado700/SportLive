package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.OrganisationDTO;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.services.OrganisationService;

@RestController
@RequestMapping("/organisation")
public class OrganisationController {

    @Autowired
    OrganisationService organisationService;

    @GetMapping("/{id}")
    public ResponseEntity<Organisation>getOrganisation(@PathVariable Integer id) {
        Organisation organisation = organisationService.getOrganisation(id);
        return new ResponseEntity<>(organisation, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Organisation>addOrganisation(@RequestBody OrganisationDTO organisationDTO){
        Organisation organisation = organisationService.addOrganisation(organisationDTO.getName(),organisationDTO.getDescription());
        return new ResponseEntity<>(organisation,HttpStatus.OK);
    }
}
