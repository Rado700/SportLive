package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.OrganisationDTO;
import ru.sportlive.mvp.dto.output.SportInfoDTO;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.services.OrganisationService;
import ru.sportlive.mvp.services.SportService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organisation")
public class OrganisationController {

    @Autowired
    OrganisationService organisationService;
    @Autowired
    SportService sportService;

    @Operation(summary = "Вывести организацию по id")
    @GetMapping("/{id}")
    public ResponseEntity<Organisation>getOrganisation(@PathVariable Integer id) {
        Organisation organisation = organisationService.getOrganisation(id);
        return new ResponseEntity<>(organisation, HttpStatus.OK);
    }

    @Operation(summary = "Добавить организацию")
    @PostMapping("/")
    public ResponseEntity<Organisation>addOrganisation(@RequestBody OrganisationDTO organisationDTO){
        Organisation organisation = organisationService.addOrganisation(organisationDTO.getName(),organisationDTO.getDescription());
        return new ResponseEntity<>(organisation,HttpStatus.OK);
    }

    @Operation(summary = "Вывести все организаций")
    @GetMapping("/")
    public ResponseEntity<List<Organisation>>getAllOrganisations(){
        List<Organisation> organisations = organisationService.getAllOrganisations();
        return new ResponseEntity<>(organisations,HttpStatus.OK);
    }

    @Operation(summary = "Удаление организаций")
    @DeleteMapping("/{id}")
    public ResponseEntity<Organisation>deleteOrganisation(@PathVariable Integer id){
        Organisation organisation = organisationService.deleteOrganisation(id);
        return new ResponseEntity<>(organisation,HttpStatus.OK);
    }

    @Operation(summary = "Обновления данных организаций")
    @PutMapping("/{id}")
    public ResponseEntity<Organisation>updateOrganisation(@PathVariable Integer id,@RequestBody OrganisationDTO organisationDTO){
        Organisation organisation = organisationService.getOrganisation(id);
        organisation = organisationService.updateToOrganisation(organisation,organisationDTO);
        return new ResponseEntity<>(organisation,HttpStatus.OK);
    }

    @Operation(summary = "Добавить спорт в организацию по id")
    @PostMapping("/sport/{sport_id}/{organisation_id}")
    public ResponseEntity<Organisation>addSport(@PathVariable Integer sport_id,@PathVariable Integer organisation_id){
        Sport sport = sportService.getSport(sport_id);
        Organisation organisation = organisationService.getOrganisation(organisation_id);
        Organisation organisation1 = organisationService.addSportForOrganisation(sport,organisation);
        return new ResponseEntity<>(organisation1,HttpStatus.OK);
    }
    @Operation(summary = "Вывести все виды спорта у организаций по id")
    @GetMapping("/sport/{id}")
    public ResponseEntity<Set<SportInfoDTO>>getAllSports(@PathVariable Integer id){
        Organisation organisation = organisationService.getOrganisation(id);
        Set<Sport> getAllSport = organisationService.getAllSportForOrganisation(organisation);
        return new ResponseEntity<>(getAllSport.stream().map(Sport::getSportInfoDTO).collect(Collectors.toSet()),HttpStatus.OK);
    }
}
