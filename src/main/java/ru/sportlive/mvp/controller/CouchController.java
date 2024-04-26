package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.CouchDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.services.CouchService;

import java.util.List;

@RestController
@RequestMapping(value="/couch")
public class CouchController {
    @Autowired
    CouchService couchService;

    @GetMapping("/couch")
    public ResponseEntity<List<Couch>>getAllUsers(){
        List<Couch>getAll = couchService.getAllCouches();
        return new ResponseEntity<>(getAll, HttpStatus.OK);

    }

    @PostMapping("/couch/add")
    public ResponseEntity<Couch>addCouch(@RequestBody CouchDTO couchDTO){
        Couch couch = couchService.addCouch(couchDTO.getName(),couchDTO.getOrganisationId());
        return new ResponseEntity<>(couch,HttpStatus.OK);
    }
}
