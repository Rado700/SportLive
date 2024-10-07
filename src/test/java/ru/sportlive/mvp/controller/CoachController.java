package ru.sportlive.mvp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.repository.CouchRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoachController {

    @Autowired
    CouchRepository couchRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void coachTest() throws Exception {

        Couch couch1 = new Couch("Tayson");
        Couch couch2 = new Couch("Nadal");
        Couch couch3 = new Couch("Marat");

        List<Couch> twoCouch = new ArrayList<>();
        twoCouch.add(couch1);
        twoCouch.add(couch2);
        twoCouch.add(couch3);

        for (Couch couches : twoCouch) {
            mockMvc.perform(post("/api/couch/")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(couches)))
                    .andExpect(status().isOk());

        }

    }
}