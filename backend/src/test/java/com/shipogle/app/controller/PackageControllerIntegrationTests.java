package com.shipogle.app.controller;

import com.shipogle.app.repository.IssueRepository;
import com.shipogle.app.repository.PackageRepository;
import com.shipogle.app.service.IssueService;
import com.shipogle.app.service.IssueServiceImpl;
import com.shipogle.app.service.PackageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class PackageControllerIntegrationTests {
    @Autowired
    MockMvc mvc;
    @Autowired
    private PackageRepository packageRepo;
    @Mock
    PackageServiceImpl packageService;
    @Mock
    Package _package;

    @Test
    public void testCreatePackage() throws Exception {
        mvc.perform(post("/package/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"package_title\": \"title\", \"description\": \"Package description\" }"))
                .andExpect(status().is(403));
    }

    @Test
    public void testGetAll() throws Exception {
        when(packageService.getPackages());
        mvc.perform(post("/package/get")
                        .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","jwt token"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatedPackage() throws Exception {
        when(packageService.updatePackage(any())).thenReturn("Package updated");
        mvc.perform(post("/package/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","jwt token"))
                .andExpect(status().isOk());
    }
}
