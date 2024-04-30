package ru.sportlive.mvp.controller;

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
public class CouchController {
    @Autowired
    CouchService couchService;

    @Autowired
    OrganisationService organisationService;

    @GetMapping("/getAllcouch")
    public ResponseEntity<List<Couch>>getAllUsers(){
        List<Couch>getAll = couchService.getAllCouches();
        return new ResponseEntity<>(getAll, HttpStatus.OK);

    }

    @PostMapping("/couch/add")
    public ResponseEntity<Couch>addCouch(@RequestBody CouchDTO couchDTO){
        try {
            Organisation organisation = organisationService.getOrganisation(couchDTO.getOrganisation_id());
            Couch couch = couchService.addCouch(couchDTO.getName(),organisation);
            return new ResponseEntity<>(couch,HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
    @DeleteMapping ("/couch/delete/{couch_id}")
    public ResponseEntity<Couch>deleteCouch(@PathVariable Integer couch_id) {
        Couch couch = couchService.deleteCouch(couch_id);
        return new ResponseEntity<>(couch,HttpStatus.OK);
    }
    @GetMapping("/getCouch/{id}")
    public ResponseEntity<Couch>getCouch(@PathVariable Integer id){
        Couch couch = couchService.getCouch(id);
        return new ResponseEntity<>(couch,HttpStatus.OK);
    }
}
