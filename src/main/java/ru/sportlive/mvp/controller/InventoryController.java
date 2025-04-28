package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.InventoryDTO;
import ru.sportlive.mvp.dto.output.UserAllInventoryDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Inventory;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    CouchService couchService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    TGService tgService;
    @Operation(summary = "Добавить инвентарь",description = "Добавить инвентарь для тренера ")
    @PostMapping("/couch/")
    public ResponseEntity<Inventory> addInventoryCouch(@RequestBody InventoryDTO inventoryDTO, HttpSession httpSession){
        Integer id = (Integer) httpSession.getAttribute("couchId");
        Couch couch = couchService.getCouch(id);
        Inventory inventory = inventoryService.addInventory(inventoryDTO.getName(),inventoryDTO.getPrice(),inventoryDTO.getType(),inventoryDTO.getSize(),couch,inventoryDTO.getAmount());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }
    @Operation(summary = "добавить инвентарь для user")
    @PostMapping("/user/{inventory_id}")
    public ResponseEntity<Inventory>addInventoryUser(@PathVariable Integer inventory_id, HttpSession httpSession) throws IOException {
        Inventory inventory = inventoryService.getInventory(inventory_id);
        Integer id = (Integer) httpSession.getAttribute("userId");
        Couch couch = inventory.getCouch();
        Login couchLoginId = loginService.getCouchLogin(couch.getId());
        String couchTelegramId = couchLoginId.getTelegramId();
        User user = userService.getUser(id);
        if (user.getBalance() < inventory.getPrice()){
            return new ResponseEntity<>(null,HttpStatus.PAYMENT_REQUIRED);
        }
        if (inventory.getAmount() == null || inventory.getAmount() == 0){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        inventoryService.addInventoryToUser(inventory,user);
        Login userLoginId = loginService.getUserLogin(user.getId());
        String userTelegramId = userLoginId.getTelegramId();
        String userMessageText;
        if (userTelegramId != null) {
            userMessageText = "Был куплен инвентраь " + inventory.getName();
        }else {
            userMessageText = null;
        }
        String couchMessageText;
        if (couchTelegramId != null) {
            couchMessageText = "Был куплен инвентарь для " + "<b><a href='tg://user?id=" + userTelegramId + "'>" + user.getName() + "</a></b>";
        }else {
            couchMessageText = "Был куплен инвентарь для <b>"+ user.getName() + "</b>";
        }
        tgService.sendMessage(couchTelegramId,couchMessageText);
        tgService.sendMessage(userTelegramId,userMessageText);
        return new ResponseEntity<>(inventory,HttpStatus.OK);

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
    @Operation(summary = "удалить инвентарь")
    @DeleteMapping("/")
    public ResponseEntity<Inventory>deleteInventory(HttpSession httpSession) {   // Доделать Удалить у тренера
        Integer id = (Integer) httpSession.getAttribute("couchId");
        Inventory inventory = inventoryService.deleteInventory(id);
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }

    @Operation(summary = "Вывести весь инвентарь")
    @GetMapping("/")
    public ResponseEntity<List<Inventory>>getAllInventory(){
        List<Inventory> inventory = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }

    @Operation(summary = "Вывести весь инвентарь тренера по id")
    @GetMapping("/couch/{id}")
    public ResponseEntity<List<Inventory>>getCouchInventory(@PathVariable Integer id){
        List<Inventory> inventory = inventoryService.getInventoryCouch(id);
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }
    @Operation(summary = "Вывести весь инвентарь тренера")
    @GetMapping("/couchInventory/")
    public ResponseEntity<List<Inventory>>getCouchInventory(HttpSession httpSession){
        Integer id = (Integer) httpSession.getAttribute("couchId");
        List<Inventory> inventory = inventoryService.getInventoryCouch(id);
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }


    @Operation(summary = "Обновить инвентарь тренера")
    @PutMapping("/{id}")
    public ResponseEntity<Inventory>updateInventory(@PathVariable Integer id, @RequestBody InventoryDTO inventoryDTO){
        Inventory inventory = inventoryService.getInventory(id);
        inventory = inventoryService.updateToInventory(inventory,inventoryDTO);
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }
    @Operation(summary = "Вывести весь инвентарь пользователя")
    @GetMapping("/userInventory/")
    public ResponseEntity<Set<UserAllInventoryDTO>>getAllInventoryUser(HttpSession httpSession){
        Integer user_id = (Integer) httpSession.getAttribute("userId");
        Set<UserAllInventoryDTO> inventory = inventoryService.getInventoryUser(user_id);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }
}
