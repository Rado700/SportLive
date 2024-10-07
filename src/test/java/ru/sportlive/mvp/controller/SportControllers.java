package ru.sportlive.mvp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.repository.SportRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SportControllers {

    @Autowired
    SportRepository sportRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void sportTest() throws Exception {

        Sport sport = new Sport("Box", "1-to-1", "fighting", "Перчатки,шлем,кеды,шорты");
        Sport sport2 = new Sport("Tennis", "1-to-1", "pool-game", "Ракетка,шорты,футболка,кеды");
        Sport sport3 = new Sport("Bodybuilding", "1 person", "bodybuilding", "Шорты,футболка,кеды");

        List<Sport> sportsList = new ArrayList<>();
        sportsList.add(sport);
        sportsList.add(sport2);
        sportsList.add(sport3);

        for (Sport sports : sportsList) {
            mockMvc.perform(post("/api/sport/")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(sports)))
                    .andExpect(status().isOk());

        }
    }
}
