//package com.shipogle.app.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shipogle.app.model.Issue;
//import com.shipogle.app.service.IssueService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
//public class IssueControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Mock
//    private IssueService issueService;
//
//    @Test
//    public void postIssueTest() throws Exception {
//        // Given
//        Map<String, String> requestBody = Collections.singletonMap("package_order_id", "1", "description", "Package was damaged");
//
//        // When
//        MvcResult result = mockMvc.perform(post("/issue/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//
//        // Then
//        assertThat(response).isEqualTo("Issue posted successfully");
//    }
//
//    @Test
//    public void getAllIssuesTest() throws Exception {
//        // Given
//        Issue issue1 = new Issue(1L, "Package was damaged");
//        Issue issue2 = new Issue(2L, "Package was lost");
//        List<Issue> issues = List.of(issue1, issue2);
//        Mockito.when(issueService.getAllIssues()).thenReturn(issues);
//
//        // When
//        MvcResult result = mockMvc.perform(get("/issue/getall")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//
//        // Then
//        assertThat(response).contains(issue1.getDescription(), issue2.getDescription());
//    }
//}
