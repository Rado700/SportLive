package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.CouchDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.OrganisationService;

import java.util.List;

@RestController
@RequestMapping("/couch")
public class CouchController {
    @Autowired
    CouchService couchService;

    @Autowired
    OrganisationService organisationService;

    @Operation(summary = "Вывести всех тренеров")
    @GetMapping("/")
    public ResponseEntity<List<Couch>>getAllUsers(){
        List<Couch>getAll = couchService.getAllCouches();
        return new ResponseEntity<>(getAll, HttpStatus.OK);

    }

    @Operation(summary = "Добавить тренера",description = "Добавть тренера и зарегестировать на организацию")
    @PostMapping("/")
    public ResponseEntity<Couch>addCouch(@RequestBody CouchDTO couchDTO){
        try {
            Organisation organisation = organisationService.getOrganisation(couchDTO.getOrganisation_id());
            Couch couch = couchService.addCouch(couchDTO.getName(),organisation);
            return new ResponseEntity<>(couch,HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
    @Operation(summary = "Удалить тренера по id")
    @DeleteMapping ("/{id}")
    public ResponseEntity<Couch>deleteCouch(@PathVariable Integer id) {
        Couch couch = couchService.deleteCouch(id);
        return new ResponseEntity<>(couch,HttpStatus.OK);
    }
    @Operation(summary = "Вывести тренера по id")
    @GetMapping("/{id}")
    public ResponseEntity<Couch>getCouch(@PathVariable Integer id){
        Couch couch = couchService.getCouch(id);
        return new ResponseEntity<>(couch,HttpStatus.OK);
    }
}
