package ru.sportlive.mvp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.sportlive.mvp.models.Inventory;
import ru.sportlive.mvp.repository.InventoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryController {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void inventoryTest() throws Exception {

        Inventory inventory = new Inventory("Перчатки", 2000, "боксерские", "10");
        Inventory inventory1 = new Inventory("Перчатки", 2500, "боксерские", "12");
        Inventory inventory2 = new Inventory("Ракетка", 3000, "теннис", "21");

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);
        inventoryList.add(inventory1);
        inventoryList.add(inventory2);

        for (Inventory inventoryes : inventoryList) {
            mockMvc.perform(post("/api/inventory/couch")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(inventoryes)))
                    .andExpect(status().isOk());

        }
    }
}
