package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sportlive.mvp.dto.input.InventoryDTO;
import ru.sportlive.mvp.dto.output.UserAllInventoryDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Inventory;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.CouchRepository;
import ru.sportlive.mvp.repository.InventoryRepository;
import ru.sportlive.mvp.repository.UserRepository;

import java.util.*;

@Transactional
@Service
public class InventoryService {


    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    CouchRepository couchRepository;
    @Autowired
    UserRepository userRepository;

    public Inventory getInventory(Integer id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory addInventory(String name, Integer price, String type, String size, Couch couch_id, Integer amount) {
        Inventory inventory = new Inventory(name, price, type, size, couch_id, amount);
        inventoryRepository.save(inventory);
        return inventory;
    }

    @Transactional
    public Inventory deleteInventory(Integer id) {
        Inventory inventory = getInventory(id);

        // Remove Inventory from Users
        for (User user : inventory.getUser()) {
            user.getSelectedInventory().remove(inventory);
        }
        inventory.getUser().clear();

        // Remove Inventory from Couch
        Couch couch = inventory.getCouch();
        if (couch != null) {
            couch.getInventory().remove(inventory);
            inventory.setCouch(null);
        }

        // Save changes to detach relationships
        inventoryRepository.save(inventory);

        // Now delete the Inventory
        inventoryRepository.delete(inventory);

        return inventory;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }


    public List<Inventory> getInventoryCouch(Integer id) {
        Optional<Couch> couch = couchRepository.findById(id);
        return couch.map(Couch::getInventory).orElse(null);
    }

    public Inventory updateToInventory(Inventory inventory, InventoryDTO inventoryDTO) {
        inventory.setName(inventoryDTO.getName());
        inventory.setPrice(inventoryDTO.getPrice());
        inventory.setType(inventoryDTO.getType());
        inventory.setSize(inventoryDTO.getSize());
        inventoryRepository.save(inventory);
        return inventory;
    }

    public void addInventoryToUser(Inventory getInventory, User user) {
        if (getInventory.getAmount() > 0) {
            user.addInventoryToUser(getInventory);
            getInventory.setAmount(getInventory.getAmount() - 1);
        }
        userRepository.save(user);
        inventoryRepository.save(getInventory);
    }

    public Set<UserAllInventoryDTO> getInventoryUser(Integer user_id) {
        Optional<User> user = userRepository.findById(user_id);
        List<Inventory> inventories = user.map(User::getSelectedInventory).orElse(null);
        if (inventories == null) {
            return null;
        }
        List<Integer> tackedInventory = new ArrayList<>();
        Set<UserAllInventoryDTO> inventoryAllPay = new HashSet<>();
        for (Inventory inventory : inventories) {
            if (tackedInventory.contains(inventory.getId())) {
                continue;
            }
            UserAllInventoryDTO userAllInventory = new UserAllInventoryDTO(
                    inventory.getId(), inventory.getName(),
                    inventory.getPrice(), inventory.getType(), inventory.getSize(),
                    Collections.frequency(inventories, inventory)
            );
            tackedInventory.add(inventory.getId());
            inventoryAllPay.add(userAllInventory);

        }
        return inventoryAllPay;
    }
}
