package com.shipogle.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.repository.PackageOrderRepository;
import com.shipogle.app.repository.PackageRepository;
import com.shipogle.app.service.PackageOrderServiceImpl;
import com.shipogle.app.service.PackageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource(locations="classpath:application-test.properties")
public class PackageOrderControllerTests {
    @Autowired
    MockMvc mvc;
    @Autowired
    private PackageOrderRepository packageOrderRepo;
    @Mock
    PackageOrderServiceImpl packageOrderService;

//    @Test
//    public void testGetALlDelivererRouteOrder() throws Exception {
//        List<PackageOrder> packageOrders = new ArrayList<>();
//        when(packageOrderService.getDelivererRouteOrders(any())).thenReturn(packageOrders);
//        mvc.perform(get("package/order/getAllDelivererRouteOrders")
//                        .contentType(MediaType.APPLICATION_JSON).queryParam("driver_route_id","1")
//                        .header("Authorization","jwt token"))
//                .andExpect(status().isOk());
//    }

    @Test
    public void testGetAllSenderOrder() throws Exception {
        List<PackageOrder> packageOrders = new ArrayList<>();
        when(packageOrderService.getSenderOrders()).thenReturn(packageOrders);
        mvc.perform(post("/package/order/getall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","jwt token"))
                .andExpect(status().isOk());
    }

    @Test
    public void testStartOrder() throws Exception{
        when(packageOrderService.startPackageOrder(1234,1)).thenReturn("Order started");
        mvc.perform(post("/package/order/start")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"pickup_code\": \"1234\", \"order_id\": \"1\" }")
                .header("Authorization","jwt token"))
                .andExpect(status().isOk());

    }

    @Test
    public void testEndOrder() throws Exception{
        when(packageOrderService.endPackageOrder(1234,1)).thenReturn("Order ended");
        mvc.perform(post("/package/order/end")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"drop_code\": \"1234\", \"order_id\": \"1\" }")
                .header("Authorization","jwt token"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRecordOrderPayment() throws Exception{
        when(packageOrderService.recordPayment(1)).thenReturn("Payment recorded");
        mvc.perform(put("/package/order/recordPayment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"order_id\": \"1\" }")
                .header("Authorization","jwt token"))
                .andExpect(status().isOk());
    }

}
