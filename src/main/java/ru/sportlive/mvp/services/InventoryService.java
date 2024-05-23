package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.dto.input.InventoryDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Inventory;
import ru.sportlive.mvp.repository.CouchRepository;
import ru.sportlive.mvp.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    CouchRepository couchRepository;

    public Inventory getInventory(Integer id){
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory addInventory(String name, Integer price, String type, String size, Couch couch_id){
        Inventory inventory = new Inventory(name,price,type,size,couch_id);
        inventoryRepository.save(inventory);
        return inventory;
    }

    public Inventory deleteInventory(Integer id){
        Inventory inventory = getInventory(id);
        inventoryRepository.delete(inventory);
        return inventory;
    }
    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }


    public List<Inventory> getInventoryCouch(Integer id){
        Optional<Couch> couch = couchRepository.findById(id);
        return couch.map(Couch::getInventory).orElse(null);
    }

    public Inventory updateToInventory(Inventory inventory, InventoryDTO inventoryDTO){
        inventory.setName(inventoryDTO.getName());
        inventory.setPrice(inventoryDTO.getPrice());
        inventory.setType(inventoryDTO.getType());
        inventory.setSize(inventoryDTO.getSize());
        inventoryRepository.save(inventory);
        return inventory;
    }

}
