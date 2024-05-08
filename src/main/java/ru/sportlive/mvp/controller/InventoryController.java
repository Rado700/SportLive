package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.InventoryDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Inventory;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    CouchService couchService;

    @Autowired
    InventoryService inventoryService;

    @Operation(summary = "Добавить инвентарь",description = "Добавить инвентарь и тренера по id")
    @PostMapping("/")
    public ResponseEntity<Inventory>addInventory(@RequestBody InventoryDTO inventoryDTO){
        Couch couch = couchService.getCouch(inventoryDTO.getCouch_id());
        Inventory inventory = inventoryService.addInventory (inventoryDTO.getName(),inventoryDTO.getPrice(),inventoryDTO.getType(),inventoryDTO.getSize(),couch);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @Operation(summary = "Вывести инвентрарь по id")
    @GetMapping("/{id}")
    public ResponseEntity<Inventory>getInventoryId(@PathVariable Integer id) {
        Inventory inventory = inventoryService.getInventory(id);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }
    @Operation(summary = "удалить инвентарь по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Inventory>deleteInventory(@PathVariable Integer id) {
        Inventory inventory = inventoryService.deleteInventory(id);
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }

    @Operation(summary = "Вывести весь инвентарь")
    @GetMapping("/")
    public ResponseEntity<List<Inventory>>getAllInventory(){
        List<Inventory> inventory = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }

    @Operation(summary = "Вывести весь инвентарь тренера")
    @GetMapping("/couch/{id}")
    public ResponseEntity<List<Inventory>>getCouchInventory(@PathVariable Integer id){
        List<Inventory> inventory = inventoryService.getInventoryCouch(id);
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }
}
