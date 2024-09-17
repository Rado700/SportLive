package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.SportSectionDTO;
import ru.sportlive.mvp.dto.output.OrganisationInfoDTO;
import ru.sportlive.mvp.dto.output.SportInfoDTO;
import ru.sportlive.mvp.dto.output.SportSectionGetAllDTO;
import ru.sportlive.mvp.dto.output.SportSectionOrganisationSportDTO;
import ru.sportlive.mvp.models.*;
import ru.sportlive.mvp.services.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/sport-section")
public class SportSectionController {

    @Autowired
    SportSectionService sportSectionService;
    @Autowired
    OrganisationService organisationService;
    @Autowired
    SportService sportService;

    @Autowired
    UserService userService;
    @Autowired
    CouchService couchService;
    @Operation(summary = "Добавить спортсекцию")
    @PostMapping("/")
    public ResponseEntity<SportSectionGetAllDTO> addSportSection(@RequestBody SportSectionDTO sportSectionDTO){
        Organisation organisation = organisationService.getOrganisation(sportSectionDTO.getOrganisation_id());
        Sport sport = sportService.getSport(sportSectionDTO.getSport_id());
        SportSection section = sportSectionService.addSportSection(sportSectionDTO.getName(),sport,organisation);
        return new ResponseEntity<>(section.getSportSectionAllDTO(), HttpStatus.OK);
    }

    @Operation(summary = "Обновить спортсекцию")
    @PutMapping("/{sport_section_id}")
    public ResponseEntity<SportSection>updateSportSection(@RequestBody SportSectionDTO sportSectionDTO,@PathVariable Integer sport_section_id){
        SportSection sportSection2 = sportSectionService.getSportSection(sport_section_id);
        Organisation organisation = organisationService.getOrganisation(sportSectionDTO.getOrganisation_id());
        Sport sport = sportService.getSport(sportSectionDTO.getSport_id());
        SportSection sportSection = sportSectionService.updateSportSection(sportSection2,sportSectionDTO.getName(),sport,organisation);
        return new ResponseEntity<>(sportSection,HttpStatus.OK);
    }

    @Operation(summary = "Вывести спортсекцию по id")
    @GetMapping("/{id}")
    public ResponseEntity<SportSection>getSportSection(@PathVariable Integer id){
        SportSection section = sportSectionService.getSportSection(id);
        return new ResponseEntity<>(section,HttpStatus.OK);
    }


    @Operation(summary = "Удалить спортсекцию по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<SportSection>deleteSportSection(@PathVariable Integer id){
        SportSection section = sportSectionService.deleteSportSection(id);
        return new ResponseEntity<>(section,HttpStatus.OK);
    }

    @Operation(summary = "Добавить user в спортсекцию")
    @PostMapping("/user/{section_id}")
    public ResponseEntity<SportSection>addUserToSportSections(@PathVariable Integer section_id, HttpSession httpSession){
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUser(userId);
        SportSection sportSection = sportSectionService.getSportSection(section_id);
        userService.addUserToSportSection(sportSection,user);
        SportSection section = sportSectionService.getSportSection(section_id);
        return new ResponseEntity<>(section,HttpStatus.OK);
    }

    @Operation(summary = "Вывести все спортсекций")
    @GetMapping("/")
    public ResponseEntity<List<SportSectionGetAllDTO>>getAllSportSections(){
        List<SportSectionGetAllDTO> sportSection = sportSectionService.getAllSportSections();
        return new ResponseEntity<>(sportSection,HttpStatus.OK);
    }

    @Operation(summary = "Вывод спортсекций по id организаций и id спорта")
    @GetMapping("/sport/organisation/{sport_id}/{organisation_id}")
    public ResponseEntity<SportSectionOrganisationSportDTO>getSportSectionOrganisationSport(@PathVariable Integer sport_id,@PathVariable Integer organisation_id){
        Sport sport = sportService.getSport(sport_id);
        SportInfoDTO sportInfoDTO = sport.getSportInfoDTO();

        Organisation organisation = organisationService.getOrganisation(organisation_id);
        OrganisationInfoDTO organisationInfoDTO = organisation.getOrganisationInfoDTO();

        SportSection section = sportSectionService.getSportOrganisation(sport,organisation);
        SportSectionOrganisationSportDTO sports = new SportSectionOrganisationSportDTO(section.getId(),sportInfoDTO,organisationInfoDTO);

        return new ResponseEntity<>(sports,HttpStatus.OK);
    }

    @Operation (summary = "Вывести спорт секцию по user id")
    @GetMapping("/user/{user_id}")
    public ResponseEntity<Set<SportSection>>getSportSectionForUser(@PathVariable Integer user_id){
        User user = userService.getUser(user_id);
        Set<SportSection> getAllSportSections = user.getSelectedSportSections();
        return new ResponseEntity<>(getAllSportSections,HttpStatus.OK);
    }

    @Operation(summary = "Добавить couch в спортсекцию")
    @PostMapping("/couch/{section_id}")
    public ResponseEntity<SportSection>addCouchToSportSections( @PathVariable Integer section_id, HttpSession httpSession){
        Integer couchId = (Integer) httpSession.getAttribute("couchId");
        if (couchId == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Couch couch = couchService.getCouch(couchId);
        SportSection sportSection = sportSectionService.getSportSection(section_id);
        couchService.addCouchToSportSection(sportSection,couch);
        SportSection section = sportSectionService.getSportSection(section_id);
        return new ResponseEntity<>(section,HttpStatus.OK);
    }

    @Operation(summary = "Добавить couch в спортсекцию по id")
    @PostMapping("/couch/{section_id}/{couch_id}")
    public ResponseEntity<SportSection>addCouchToSportSections( @PathVariable Integer section_id, @PathVariable Integer couch_id){
        Couch couch = couchService.getCouch(couch_id);
        SportSection sportSection = sportSectionService.getSportSection(section_id);
        couchService.addCouchToSportSection(sportSection,couch);
        SportSection section = sportSectionService.getSportSection(section_id);
        return new ResponseEntity<>(section,HttpStatus.OK);
    }

}
