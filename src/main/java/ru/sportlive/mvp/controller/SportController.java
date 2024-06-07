package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.SportDTO;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.services.SportService;

import java.util.List;

@RestController
@RequestMapping("/api/sport")
public class SportController {
    @Autowired
    SportService sportService;//TODO: Протестировать получение

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

    @Operation(summary = "Удалить вид спорта по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Sport>deleteSport(@PathVariable Integer id){
        Sport sport = sportService.deleteSport(id);
        return new ResponseEntity<>(sport,HttpStatus.OK);
    }

    @Operation(summary = "Вывести вид спорта по id")
    @GetMapping("/{id}")
    public ResponseEntity<Sport>getSportById(@PathVariable Integer id){
        Sport sport = sportService.getSport(id);
        return new ResponseEntity<>(sport,HttpStatus.OK);
    }
    @Operation(summary = "Обновления данных у спорта")
    @PutMapping("/{id}")
    public ResponseEntity<Sport>updateSport(@PathVariable Integer id,@RequestBody SportDTO sportDTO){
        Sport sport = sportService.getSport(id);
        sport = sportService.updateToSport(sport,sportDTO);
        return new ResponseEntity<>(sport,HttpStatus.OK);
    }

}
