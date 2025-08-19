//package ru.sportlive.mvp.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.sportlive.mvp.models.Login;
//import ru.sportlive.mvp.repository.LoginRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class LoginControllers {
//
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    LoginRepository loginRepository;
//
////
////    @BeforeEach
////    public void setup() {
////        userRepository.deleteAll();
////    }
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    public void loginTest() throws Exception {
//
//
//        Login login1 = new Login("radik1","1111");
//        Login login2 = new Login("rafail2","2222");
//        Login login3 = new Login("anton3","3333");
//        Login login4 = new Login("sergei4","4444");
//
//        List<Login> twoPerson = new ArrayList<>();
//
//        twoPerson.add(login1);
//        twoPerson.add(login2);
//        twoPerson.add(login3);
//        twoPerson.add(login4);
//
//        for (Login logins : twoPerson) {
//            mockMvc.perform(post("/api/login/user/registration/")
//                            .contentType("application/json")
//                            .content(objectMapper.writeValueAsString(logins)))
//                    .andExpect(status().isOk());
//        }
//
//
////        MvcResult result= mockMvc.perform(get("/api/user/all"))
////                .andExpect(status().isOk())
////               .andReturn();
////        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
////        assertTrue(jsonResponse.contains("\"name\":\"Антон\""),"Не найдено имя Антон");
//
////        List<User> usersResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<User>>() {});
////        assertTrue(usersResponse.stream().allMatch(u -> u.getName().equals("Sergei")));
//    }
//
//}
//
//
