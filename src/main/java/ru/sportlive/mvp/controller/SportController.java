package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.SportDTO;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.services.SportService;

import java.util.List;

@RestController
@RequestMapping("/sport")
public class SportController {
    @Autowired
    SportService sportService;

    @Operation(summary = "Добавить вид спорта",description = "Наименование спорта,комментарий,инструкцию,экипировка(оборудование)")
    @PostMapping("/")
    public ResponseEntity<Sport>addSport(@RequestBody SportDTO sportDTO){
        Sport sport = sportService.addSport(sportDTO.getName_sport(),sportDTO.getDescription(),sportDTO.getInstruction(),sportDTO.getEquipment());
        return new ResponseEntity<>(sport, HttpStatus.OK);
    }

    @Operation(summary = "Вывести все виды спорта")
    @GetMapping("/")
    public ResponseEntity<List<Sport>>getAllSport(){
        List<Sport>sports = sportService.getAllSport();
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }

}
