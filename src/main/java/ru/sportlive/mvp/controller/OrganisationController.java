package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.OrganisationDTO;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.services.OrganisationService;

import java.util.List;

@RestController
@RequestMapping("/api/organisation")
public class OrganisationController {

    @Autowired
    OrganisationService organisationService;

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
}
