package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sportlive.mvp.dto.SportDTO;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.services.SportService;

@RestController
@RequestMapping("/sport")
public class SportController {
    @Autowired
    SportService sportService;

    @PostMapping("/")
    public ResponseEntity<Sport>addSport(@RequestBody SportDTO sportDTO){
        Sport sport = sportService.addSport(sportDTO.getName_sport(),sportDTO.getDescription(),sportDTO.getInstruction(),sportDTO.getEquipment());
        return new ResponseEntity<>(sport, HttpStatus.OK);
    }
}
