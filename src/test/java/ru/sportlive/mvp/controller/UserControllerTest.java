package ru.sportlive.mvp.controller;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;
//
//    @BeforeEach
//    public void setup() {
//        userRepository.deleteAll();
//    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void userTest ()throws Exception{

//        User user1 = new User("Galimov","Radik",175,73);
//        User user2 = new User("Rafail","Venediktov",173,74);

        User user3 = new User("Anton","Antonov",177,79);
        User user4 = new User("Sergei","Sergeev",185,85);

        List<User>twoPerson = new ArrayList<>();
        twoPerson.add(user3);
        twoPerson.add(user4);

        for (User users:twoPerson) {
            mockMvc.perform(post("/api/user/")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(users)))
                    .andExpect(status().isOk());
        }


//        MvcResult result= mockMvc.perform(get("/api/user/all"))
//                .andExpect(status().isOk())
//               .andReturn();
//        String jsonResponse = result.getResponse().getContentAsString();
//        List<User> usersResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<User>>() {});
//        assertTrue(usersResponse.stream().allMatch(u -> u.getName().equals("Anton")));
//        assertTrue(usersResponse.stream().allMatch(u -> u.getName().equals("Sergei")));
    }
}
