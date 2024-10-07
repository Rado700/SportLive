package ru.sportlive.mvp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.repository.OrganisationRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganisationControllers {

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void organisationTest() throws Exception {

        Organisation organisation = new Organisation("FitnessLand", "Fitness");
        Organisation organisation2 = new Organisation("TennisCorporation", "Tennis");
        Organisation organisation3 = new Organisation("BoxingCorporation", "Boxing");

        List<Organisation> organisationList = new ArrayList<>();
        organisationList.add(organisation);
        organisationList.add(organisation2);
        organisationList.add(organisation3);

        for (Organisation organisations : organisationList) {
            mockMvc.perform(post("/api/organisation/")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(organisations)))
                    .andExpect(status().isOk());

        }
    }
}
