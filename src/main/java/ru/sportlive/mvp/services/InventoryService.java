package ru.sportlive.mvp.services;

import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Inventory;
import ru.sportlive.mvp.repository.InventoryRepository;

import java.util.List;

@Service
public class InventoryService {

    InventoryRepository inventoryRepository;

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
}
