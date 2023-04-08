package com.shipogle.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.ShipogleApplication;
import com.shipogle.app.filter.Authfilter;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.UserRepository;
import com.shipogle.app.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashMap;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestPropertySource(locations = "classpath:application-test.properties")

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource(locations="classpath:application-test.properties")
public class AuthControllerIntegrationTests {
    @InjectMocks
    AuthController authController;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Mock
    AuthServiceImpl authService;
    @Autowired
    MockMvc mvc;
    @Mock
    Authfilter authfilter;

    @Test
    public void loginTest() throws Exception {
        Map<String, String> req = new HashMap<>();
        req.put("email","kadivarnand007@gmail.com");
        req.put("password","abc123");

        authController.login(req);

        verify(authService,times(1)).login("kadivarnand007@gmail.com","abc123");
    }

//    @Test
//    void testRegisterAlreadyRegisteredUser() throws Exception {
//        User user = new User();
//        user.setFirst_name("Nand");
//        user.setLast_name("Kadivar");
//        user.setEmail("kadivarnand007@gmailc.om");
//        user.setPassword("password123");
//
//        mvc.perform(post("/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user)))
//                .andExpect(status().is(400)).andExpect(status().reason("User Already exist with this email"));
//    }
//
//    @Test
//    void testLoginInvalid() throws Exception {
//        mvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"email\": \"kadivarnand007@gmail.com\", \"password\": \"abc123456\" }"))
//                .andExpect(status().is(401)).andExpect(status().reason("Bad credentials"));
//    }
//
//    @Test
//    void testLoginSuccess() throws Exception {
//
//        mvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"email\": \"kadivarnand007@gmail.com\", \"password\": \"abc123\" }"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testChangePassword() throws Exception {
//
//        mvc.perform(post("/changepassword")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"token\": \"token\", \"password\": \"abc123\" }"))
//                .andExpect(status().is(400));
//    }
//
//    @Test
//    void testEmailVerification() throws Exception {
//
//        mvc.perform(get("/verification?code=$2a$10$qo0V9.z7cDhOhOUWFpp2nuDKOTtAarnx4/3eKHDEXwUi9V39DymPy&id=863"))
//                .andExpect(status().is(400)).andExpect(status().reason("403 FORBIDDEN \"Not valid user\""));
//    }
}

