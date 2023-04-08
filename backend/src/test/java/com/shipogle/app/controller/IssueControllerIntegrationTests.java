package com.shipogle.app.controller;

import com.shipogle.app.filter.Authfilter;
import com.shipogle.app.repository.IssueRepository;
import com.shipogle.app.repository.UserRepository;
import com.shipogle.app.service.IssueService;
import com.shipogle.app.service.IssueServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class IssueControllerIntegrationTests {
    @Autowired
    MockMvc mvc;
    @Autowired
    private IssueRepository issueRepo;
    @Autowired
    IssueServiceImpl issueService;
    @Mock
    IssueService issueSer;

    @Test
    void testPostIssue() throws Exception {
        mvc.perform(post("/issue/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"package_order_id\": \"449\", \"description\": \"Issue description\" }"))
                .andExpect(status().is(403));
    }

//    @Test
//    void testPostIssueAuth() throws Exception {
//        when(issueSer.postIssue(Integer.valueOf(449),"Issue description")).thenReturn("Issue posted");
//
//        mvc.perform(post("/issue/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization","jwt token")
//                        .content("{ \"package_order_id\": \"449\", \"description\": \"Issue description\" }"))
//                .andExpect(status().isOk());
//    }

    @Test
    void testgetAllIssues() throws Exception {
        when(issueSer.getAllIssues());
        mvc.perform(post("/issue/getall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","jwt token")
        ).andExpect(status().isOk());
    }
}