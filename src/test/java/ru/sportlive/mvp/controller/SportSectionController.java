package ru.sportlive.mvp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.models.Sport;
import ru.sportlive.mvp.models.SportSection;
import ru.sportlive.mvp.repository.OrganisationRepository;
import ru.sportlive.mvp.repository.SportRepository;
import ru.sportlive.mvp.repository.SportSectionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SportSectionController {

    @Autowired
    SportSectionRepository sportSectionRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SportRepository sportRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Test
    public void sportSectionTest()throws Exception{
        Sport sport1 = new Sport();
        Sport sport2= new Sport();
        List<Sport> sport = sportRepository.findAll();
        for (int i = 0; i < sport.size(); i++) {
            sport1 = sport.get(0);
            sport2 = sport.get(1);
        }
        Organisation organisation1 = new Organisation();
        Organisation organisation2 = new Organisation();
        List<Organisation>organisations = organisationRepository.findAll();
        for (int i = 0; i < organisations.size(); i++) {
            organisation1 = organisations.get(0);
            organisation2 = organisations.get(1);
        }

        SportSection section = new SportSection("BoxStudio",sport1,organisation1);
        SportSection section2 = new SportSection("BoxStudio",sport2,organisation2);

        List<SportSection>sportSections = new ArrayList<>();
        sportSections.add(section);
        sportSections.add(section2);

        for (SportSection sections:sportSections) {
            mockMvc.perform(post("/api/sportSection/")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(sections)))
                    .andExpect(status().isOk());
        }
    }
}
