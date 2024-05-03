package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sportlive.mvp.dto.InventoryDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Inventory;
import ru.sportlive.mvp.services.CouchService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    CouchService couchService;

    @PostMapping("/")
    public ResponseEntity<Inventory>addInventory(@RequestBody InventoryDTO inventoryDTO){
        Couch couch = couchService.getCouch(inventoryDTO.getCouch_id());
        Inventory inventory = couchService.addInventory (inventoryDTO.getName(),inventoryDTO.getPrice(),inventoryDTO.getType(),inventoryDTO.getSize(),couch);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }
}
