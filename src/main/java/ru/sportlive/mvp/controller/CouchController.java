package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.CouchDTO;
import ru.sportlive.mvp.dto.input.CouchOrganisationDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.models.SportSection;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.OrganisationService;
import ru.sportlive.mvp.services.SportSectionService;
import ru.sportlive.mvp.services.SportService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/couch")
public class CouchController {
    @Autowired
    CouchService couchService;

    @Autowired
    OrganisationService organisationService;
    @Autowired
    SportService sportService;

    @Autowired
    SportSectionService sportSectionService;

    @Operation(summary = "Вывести всех тренеров")
    @GetMapping("/all/")
    public ResponseEntity<List<Couch>> getAllUsers() {
        List<Couch> getAll = couchService.getAllCouches();
        return new ResponseEntity<>(getAll, HttpStatus.OK);

    }

    @Operation(summary = "Добавить тренера", description = "Добавть тренера и зарегестировать на организацию")
    @PostMapping("/")
    public ResponseEntity<Couch> addCouch(@RequestBody CouchDTO couchDTO, HttpSession httpSession) {
        try {
            Couch couch = couchService.addCouch(couchDTO.getName(), new ArrayList<>());
            httpSession.setAttribute("couchId",couch.getId());
            return new ResponseEntity<>(couch, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Удалить тренера по id")
    @DeleteMapping("/{couchId}")
    public ResponseEntity<Couch> deleteCouch(HttpSession httpSession, Integer couchId) {
        if (couchId == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Couch couch = couchService.deleteCouch(couchId);
        return new ResponseEntity<>(couch, HttpStatus.OK);
    }

    @Operation(summary = "Вывести тренера по id")
    @GetMapping("/{id}")
    public ResponseEntity<Couch> getCouch(@PathVariable Integer id) {
        Couch couch = couchService.getCouch(id);
        return new ResponseEntity<>(couch, HttpStatus.OK);
    }
    @Operation(summary = "Вывести тренера ")
    @GetMapping("/getCouch/")
    public ResponseEntity<Couch> getCouchAuth(HttpSession httpSession) {
        Integer id = (Integer) httpSession.getAttribute("couchId");
        if (id == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Couch couch = couchService.getCouch(id);
        return new ResponseEntity<>(couch, HttpStatus.OK);
    }
    @Operation(summary = "Вывести тренеров у организаций по id")
    @GetMapping("/organisation/{id}")
    public ResponseEntity<List<Couch>>getAllCouchForOrganisation(@PathVariable Integer id){
        Organisation organisation = organisationService.getOrganisation(id);
        List<Couch>getAll = organisation.getCouches() ;
        return new ResponseEntity<>(getAll,HttpStatus.OK);
    }

    @Operation(summary = "Добавить нового тренера в спортивную секцию")
    @PostMapping("/sport-section/")
    public ResponseEntity<Object> addCouchForOrganisation(@RequestBody CouchOrganisationDTO couchDTO,HttpSession httpSession) {
        SportSection sportSection = sportSectionService.getSportSection(couchDTO.getOrganisation_id());
        Couch couch = couchService.getCouch((Integer) httpSession.getAttribute("couchId"));
        if (couch == null){
            return new ResponseEntity<>("Такого тренера не существует",HttpStatus.BAD_REQUEST);
        }
        if (sportSection == null){
            return new ResponseEntity<>("Такой организаций не существует",HttpStatus.BAD_REQUEST);
        }
        Couch couch1 = couchService.addCouchToSportSection(sportSection, couch);
        return new ResponseEntity<>(couch1,HttpStatus.OK);
    }
    @Operation(summary = "Добавить нового тренера в организацию по id")
    @PostMapping("/organisation/{organisation_id}/{couch_id}")
    public ResponseEntity<Object> addCouchForOrganisation(@PathVariable Integer organisation_id, @PathVariable Integer couch_id) {
        Organisation organisation = organisationService.getOrganisation(organisation_id);
        Couch couch = couchService.getCouch(couch_id);
        if (couch == null){
            return new ResponseEntity<>("Такого тренера не существует",HttpStatus.BAD_REQUEST);
        }
        if (organisation == null){
            return new ResponseEntity<>("Такой организаций не существует",HttpStatus.BAD_REQUEST);
        }
        Organisation organisation1 = couchService.addOrganisationToCouch(organisation, couch);
        return new ResponseEntity<>(organisation1,HttpStatus.OK);
    }

    @Operation(summary = "Добавить нового тренера в организацию")
    @PostMapping("/organisation/{organisation_id}")
    public ResponseEntity<Object> addCouchForOrganisation(@PathVariable Integer organisation_id, HttpSession httpSession) {
        Organisation organisation = organisationService.getOrganisation(organisation_id);
        Integer couch_id = (Integer) httpSession.getAttribute("couchId");
        Couch couch = couchService.getCouch(couch_id);
        if (couch == null){
            return new ResponseEntity<>("Такого тренера не существует",HttpStatus.BAD_REQUEST);
        }
        if (organisation == null){
            return new ResponseEntity<>("Такой организаций не существует",HttpStatus.BAD_REQUEST);
        }
        Organisation organisation1 = couchService.addOrganisationToCouch(organisation, couch);
        return new ResponseEntity<>(organisation1,HttpStatus.OK);
    }


    @Operation(summary = "Обновления данных у тренера")
    @PutMapping("/")
    public ResponseEntity<Couch>updateCouch(@RequestBody CouchDTO couchDTO,HttpSession httpSession){
        Couch couch = couchService.getCouch((Integer) httpSession.getAttribute("couchId"));
        couch = couchService.updateToCouch(couch,couchDTO);
        return new ResponseEntity<>(couch,HttpStatus.OK);
    }
//    @Operation(summary = "Вывести все виды спорта у тренера по id")
//    @GetMapping("/{couch_id}")
//    public ResponseEntity<List<Sport>>getAllSportForCouch(@PathVariable Integer couch_id){
//        Couch couch = couchService.getCouch(couch_id);
//        List<Sport>getAllSport = sportService.getAllSportForCouch(couch);
//        return new ResponseEntity<>(getAllSport,HttpStatus.OK);
//    }

    @Operation(summary = "Вывести всех тренеров у спорта и организаций")
    @GetMapping("/sport/organisation/{sport_id}/{organisation_id}")
    public ResponseEntity<List<Couch>>getAllCouchToSportAndOrganisation(@PathVariable Integer sport_id,@PathVariable Integer organisation_id){
        Sport sport = sportService.getSport(sport_id);
        Organisation organisation = organisationService.getOrganisation(organisation_id);
        List<Couch>allCouchBySport = sportService.getAllCouchForSportOrganisation(sport, organisation);
        return new ResponseEntity<>(allCouchBySport, HttpStatus.OK);
    }
}


//GET /scedule/couch/{couchId} выводить расписание у тренера +
//        GET /inventory/couch/{couchId} выводить весь инвентарь тренера+
//        POST /couch/organization добавлять тренера в организацию. В тело передавать couch_id и organization_id
//        GET /booking/user/{userId} все бронирования пользователя+
//        GET /booking/couch/{couchId} все бронирования тренера+
//Сортировка по времени в /scedule/couch/{couchId}+


//GET /booking/couch/user/{couchId}/{userId} - выводить список всех броней, которые были проведены тренером с couchId для юзера userId

