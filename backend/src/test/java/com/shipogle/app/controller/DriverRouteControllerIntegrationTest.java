
package com.shipogle.app.controller;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.model.DashboardFilter;
import com.shipogle.app.model.DriverRoute;
import com.shipogle.app.repository.DriverRouteRepository;
import com.shipogle.app.service.DriverRouteFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DriverRouteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverRouteFilter driverRouteFilter;
    private String token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InF1cGlmdWdpdG9AZ290Z2VsLm9yZyIsInN1YiI6IlNoaXZhbSIsImlhdCI6MTY4MDkzNjQ2MCwiZXhwIjoxNjgxMDIyODYwfQ.dQ5bC5Nb88ZyRIdoARdksAweYoA82sciLFiCKI4Rfk29k2XVcpYZPXogNDEYTdpH";

    @Autowired
    private DriverRouteRepository driverRouteRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private DriverRoute createDriverRoute(String driverId, String sourceCity, String sourceCityReferenceId,
                                          String destination, String destinationCityReferenceId, int maxPackages,
                                          int maxLength, int maxWidth, int maxHeight, Date pickupDate,
                                          Date dropoffDate, int daysToDeliver, List<Double> pickupLocationCoords,
                                          List<Double> dropoffLocationCoords, List<String> allowedCategory, int radius,
                                          int price) {
        DriverRoute driverRoute = new DriverRoute();
        driverRoute.setDriverId(driverId);
        driverRoute.setSourceCity(sourceCity);
        driverRoute.setSourceCityReferenceId(sourceCityReferenceId);
        driverRoute.setDestinationCity(destination);
        driverRoute.setDestinationCityReferenceId(destinationCityReferenceId);
        driverRoute.setMaxPackages(maxPackages);
        driverRoute.setMaxLength(maxLength);
        driverRoute.setMaxWidth(maxWidth);
        driverRoute.setMaxHeight(maxHeight);
        driverRoute.setPickupDate(pickupDate);
        driverRoute.setDropoffDate(dropoffDate);
        driverRoute.setDaysToDeliver(daysToDeliver);
        driverRoute.setPickupLocationCoords(pickupLocationCoords);
        driverRoute.setDropoffLocationCoords(dropoffLocationCoords);
        driverRoute.setAllowedCategory(allowedCategory);
        driverRoute.setRadius(radius);
        driverRoute.setPrice(price);

        return driverRoute;
    }

    private void saveDriverRoute(DriverRoute driverRoute) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(driverRoute);

        mockMvc.perform(post("/driverRoutes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(String.format("Driver Details saved : \n%s", driverRoute)))
                .andReturn();
    }

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void tearDown() {
        //driverRouteRepository.deleteAll();
    }

    @Test
    public void testCreateDriverRoute() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       // Date date = format.parse("2023-05-01");
        DriverRoute driverRoute = createDriverRoute(
                String.valueOf(5678),
                "New York",
                "NYC",
                "Chicago",
                "CHI",
                3,
                12,
                10,
                8,
                format.parse("2023-05-01"),
                format.parse("2023-05-05"),
                3,
                Arrays.asList(40.7128, -74.006),
                Arrays.asList(41.8781, -87.6298),
                Arrays.asList("books", "toys"),
                100,
                150
        );

        driverRoute.setDriverRouteId(1234L);
        ObjectMapper objectMapper = new ObjectMapper();
        String driverRouteJson = objectMapper.writeValueAsString(driverRoute);

        mockMvc.perform(post("/driverRoutes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InNoaXBvZ2xlLnRlc3R1c2VyMUB5b3BtYWlsLmNvbSIsInN1YiI6IlRlc3QiLCJpYXQiOjE2ODA5OTAwNjN9.b4DlK4cXAOzYAnZsFl5xAFFvIMJrv85QyMYtf-koS_Jq4h4UA6BHlDc1fmrdaZ9P")
                        .content(driverRouteJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetDriverRoutesByFilters() throws Exception {
        mockMvc.perform(get("/driverRoutes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sourceCity", "New York")
                        .param("destination", "Chicago")
                        .param("pickupDataTime", "2023-05-01T08:00:00")
                        .param("maxPackages", "3")
                        .param("allowedCategory", "books,toys")
                        .param("radius", "100")
                        .param("price", "150")
                        .param("category", "books"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDriverRoutesByDriverId() throws Exception {
        mockMvc.perform(get("/driverRoutesByDriverId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InNoaXBvZ2xlLnRlc3R1c2VyMUB5b3BtYWlsLmNvbSIsInN1YiI6IlRlc3QiLCJpYXQiOjE2ODA5OTAwNjN9.b4DlK4cXAOzYAnZsFl5xAFFvIMJrv85QyMYtf-koS_Jq4h4UA6BHlDc1fmrdaZ9P")
                        .param("driverId", "372"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testGetDriverRoutesByFilters() throws Exception {
//        // Create some dummy data
//        DriverRoute route1 = createDriverRoute("1234", "New York", "NYC", "Chicago", "CHI", 3, 12, 10, 8, new Date(), new Date(), 3, Arrays.asList(40.7128, -74.006), Arrays.asList(41.8781, -87.6298), Arrays.asList("books", "toys"), 100, 150);
//        DriverRoute route2 = createDriverRoute("5678", "Los Angeles", "LA", "San Francisco", "SF", 2, 8, 6, 4, new Date(), new Date(), 2, Arrays.asList(34.0522, -118.2437), Arrays.asList(37.7749, -122.4194), Arrays.asList("electronics", "clothing"), 200, 250);
//
//        // Set up the mock filter object to return the dummy data
//        DashboardFilter mockFilter = Mockito.mock(DashboardFilter.class);
//        Mockito.when(mockFilter.getDriverRoutesByFilters(Mockito.any(DashboardFilter.class))).thenReturn(Arrays.asList(route1, route2));
//
//        // Call the API endpoint with some filter parameters
//        MvcResult result = mockMvc.perform(get("/driverRoutes")
//                        .param("sourceCity", "New York")
//                        .param("destination", "Chicago")
//                        .param("pickupDataTime", "2023-05-01")
//                        .param("maxPackages", "3")
//                        .param("allowedCategory", "books")
//                        .param("radius", "50")
//                        .param("price", "100-200")
//                        .param("category", "electronics"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // Verify that the correct data was returned
//        String responseBody = result.getResponse().getContentAsString();
//        List<DriverRoute> routes = new ObjectMapper().readValue(responseBody, new TypeReference<List<DriverRoute>>() {});
//        assertEquals(2, routes.size());
//        assertEquals(route1, routes.get(0));
//        assertEquals(route2, routes.get(1));
//    }




}


