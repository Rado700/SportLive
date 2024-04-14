package ru.sportlive.mvp.controller;

import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.services.CouchService;

import java.util.List;

@RestController
@RequestMapping(value="/couch")
public class CouchController {
    @Autowired
    CouchService couchService;

    @GetMapping("/users")
    public ResponseEntity<List<Couch>>getAllUsers(){
        List<Couch>getAll = couchService.getAllUsers();
        return new ResponseEntity<>(getAll, HttpStatus.OK);

    }
}
