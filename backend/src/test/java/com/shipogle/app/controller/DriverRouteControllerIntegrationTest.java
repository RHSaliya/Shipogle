//package com.shipogle.app.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shipogle.app.model.DriverRoute;
//import com.shipogle.app.repository.DriverRouteRepository;
//import com.shipogle.app.service.DriverRouteFilter;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class DriverRouteControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private DriverRouteFilter driverRouteFilter;
//    private String token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InF1cGlmdWdpdG9AZ290Z2VsLm9yZyIsInN1YiI6IlNoaXZhbSIsImlhdCI6MTY4MDkzNjQ2MCwiZXhwIjoxNjgxMDIyODYwfQ.dQ5bC5Nb88ZyRIdoARdksAweYoA82sciLFiCKI4Rfk29k2XVcpYZPXogNDEYTdpH";
//
//    @Autowired
//    private DriverRouteRepository driverRouteRepository;
//
//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//
//    private DriverRoute createDriverRoute(String driverId, String sourceCity, String sourceCityReferenceId,
//                                          String destination, String destinationCityReferenceId, int maxPackages,
//                                          int maxLength, int maxWidth, int maxHeight, Date pickupDate,
//                                          Date dropoffDate, int daysToDeliver, List<Double> pickupLocationCoords,
//                                          List<Double> dropoffLocationCoords, List<String> allowedCategory, int radius,
//                                          int price) {
//        DriverRoute driverRoute = new DriverRoute();
//        driverRoute.setDriverId(driverId);
//        driverRoute.setSourceCity(sourceCity);
//        driverRoute.setSourceCityReferenceId(sourceCityReferenceId);
//        driverRoute.setDestinationCity(destination);
//        driverRoute.setDestinationCityReferenceId(destinationCityReferenceId);
//        driverRoute.setMaxPackages(maxPackages);
//        driverRoute.setMaxLength(maxLength);
//        driverRoute.setMaxWidth(maxWidth);
//        driverRoute.setMaxHeight(maxHeight);
//        driverRoute.setPickupDate(pickupDate);
//        driverRoute.setDropoffDate(dropoffDate);
//        driverRoute.setDaysToDeliver(daysToDeliver);
//        driverRoute.setPickupLocationCoords(pickupLocationCoords);
//        driverRoute.setDropoffLocationCoords(dropoffLocationCoords);
//        driverRoute.setAllowedCategory(allowedCategory);
//        driverRoute.setRadius(radius);
//        driverRoute.setPrice(price);
//
//        return driverRoute;
//    }
//
//    private void saveDriverRoute(DriverRoute driverRoute) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(driverRoute);
//
//        mockMvc.perform(post("/driverRoutes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString))
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().string(String.format("Driver Details saved : \n%s", driverRoute)))
//                .andReturn();
//    }
//
//    @BeforeEach
//    public void setup() {
//
//    }
//
//    @AfterEach
//    public void tearDown() {
//        //driverRouteRepository.deleteAll();
//    }
//
//    @Test
//    public void testCreateDriverRoute() throws Exception {
//        DriverRoute driverRoute = createDriverRoute(
//                "5678",
//                "New York",
//                "NYC",
//                "Chicago",
//                "CHI",
//                3,
//                12,
//                10,
//                8,
//                new Date("2023-05-01"),
//                new Date("2023-05-05"),
//                3,
//                Arrays.asList(40.7128, -74.006),
//                Arrays.asList(41.8781, -87.6298),
//                Arrays.asList("books", "toys"),
//                100,
//                150
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String driverRouteJson = objectMapper.writeValueAsString(driverRoute);
//
//        mockMvc.perform(post("/driverRoutes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization",
//                                "Bearer " + token + "\"")
//                        .content(driverRouteJson))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void testGetDriverRoutesByFilters() throws Exception {
//        mockMvc.perform(get("/driverRoutes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("sourceCity", "New York")
//                        .param("destination", "Chicago")
//                        .param("pickupDataTime", "2023-05-01T08:00:00")
//                        .param("maxPackages", "3")
//                        .param("allowedCategory", "books,toys")
//                        .param("radius", "100")
//                        .param("price", "150")
//                        .param("category", "books"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetDriverRoutesByDriverId() throws Exception {
//        mockMvc.perform(get("/driverRoutesByDriverId")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization",
//                                "Bearer " + token + "\"")
//                        .param("driverId", "372"))
//                .andExpect(status().isOk());
//    }
//}
//
