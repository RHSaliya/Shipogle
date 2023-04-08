package com.shipogle.app.service;

import com.shipogle.app.model.*;
import com.shipogle.app.model.Package;
import com.shipogle.app.repository.PackageOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class PackageOrderServiceTests {
    @InjectMocks
    PackageOrderService packageOrderService;
    @Mock
    PackageOrder packageOrder;
    @Mock
    PackageOrderRepository packageOrderRepo;
    @Mock
    PackageRequest packageRequest;
    @Mock
    Package _package;
    @Mock
    User user;
    @Mock
    DriverRoute driverRoute;
    @Mock
    UserService userService;

//    @Test
//    public void createPackageOrderTestSuccess(){
//        Mockito.when(packageRequest.get_package()).thenReturn(_package);
//        Mockito.when(packageRequest.getDeliverer()).thenReturn(user);
//        Mockito.when(packageRequest.getSender()).thenReturn(user);
//        Mockito.when(packageRequest.getDriverRoute()).thenReturn(driverRoute);
//
//        Mockito.lenient().when(packageOrderRepo.save(packageOrder)).thenReturn(packageOrder);
//
//        assertEquals("order created",packageOrderService.createPackageOrder(packageRequest));
//    }

    @Test
    public void cancelOrderTestOrderNotFound(){
        Mockito.when(packageOrderRepo.getPackageOrderById(10)).thenReturn(null);
        assertThrows(ResponseStatusException.class,()->packageOrderService.cancelOrder(10));
    }

    @Test
    public void cancelOrderTestAlreadyCanceled(){
        Mockito.when(packageOrderRepo.getPackageOrderById(10)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isCanceled()).thenReturn(true);
        assertThrows(ResponseStatusException.class,()->packageOrderService.cancelOrder(10));
    }

    @Test
    public void cancelOrderTestAlreadyStarted(){
        Mockito.when(packageOrderRepo.getPackageOrderById(10)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isStarted()).thenReturn(true);
        assertThrows(ResponseStatusException.class,()->packageOrderService.cancelOrder(10));
    }

    @Test
    public void cancelOrderTestSuccess(){
        Mockito.when(packageOrderRepo.getPackageOrderById(10)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isStarted()).thenReturn(false);
        Mockito.when(packageOrder.isStarted()).thenReturn(false);
        Mockito.lenient().when(packageOrderRepo.save(packageOrder)).thenReturn(packageOrder);
        assertEquals("order is canceled",packageOrderService.cancelOrder(10));
    }

    @Test
    public void getSenderOrdersTestSuccess(){
        List<PackageOrder> orders = new ArrayList<>();
        Mockito.when(userService.getLoggedInUser()).thenReturn(user);
        Mockito.when(packageOrderRepo.getAllBySender(user)).thenReturn(orders);

        assertEquals(orders,packageOrderService.getSenderOrders());
    }

    @Test
    public void getSenderOrdersTestUserNotFound(){
        Mockito.when(userService.getLoggedInUser()).thenThrow(UsernameNotFoundException.class);
        assertThrows(ResponseStatusException.class,()->packageOrderService.getSenderOrders());
    }

    @Test
    public void getDelivererRouteOrdersTestSuccess(){
        List<PackageOrder> orders = new ArrayList<>();
        Mockito.when(packageOrderRepo.getAllByDriverRoute_Id(1L)).thenReturn(orders);
        assertEquals(orders,packageOrderService.getDelivererRouteOrders(1L));
    }

    @Test
    public void getDelivererRouteOrdersTestInvalidRoute(){
        Mockito.when(packageOrderRepo.getAllByDriverRoute_Id(1L)).thenThrow(RuntimeException.class);
        assertThrows(ResponseStatusException.class,()->packageOrderService.getDelivererRouteOrders(1L));
    }

    @Test
    public void recordPaymentTestInvalidOrder(){
        Mockito.when(packageOrderRepo.getById(1)).thenThrow(RuntimeException.class);
        assertThrows(ResponseStatusException.class,()->packageOrderService.recordPayment(1));
    }

    @Test
    public void recordPaymentTestSuccess(){
        Mockito.lenient().when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        Mockito.lenient().when(packageOrderRepo.save(packageOrder)).thenReturn(packageOrder);
        assertEquals("Payment is recorded",packageOrderService.recordPayment(1));
    }

    @Test
    public void startPackageOrderTestCanceledOrder(){
        Mockito.when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isCanceled()).thenReturn(true);
        assertThrows(ResponseStatusException.class,()->packageOrderService.startPackageOrder(1254,1));
    }

    @Test
    public void startPackageOrderTestSuccess(){
        Mockito.when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isCanceled()).thenReturn(false);
        Mockito.when(packageOrder.getPickup_code()).thenReturn(1234);
        assertEquals("Order started",packageOrderService.startPackageOrder(1234,1));
    }

    @Test
    public void startPackageOrderTestInvalidCode(){
        Mockito.when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isCanceled()).thenReturn(false);
        Mockito.when(packageOrder.getPickup_code()).thenReturn(1226);
        assertThrows(ResponseStatusException.class,()->packageOrderService.startPackageOrder(1254,1));
    }

    @Test
    public void endPackageOrderTestOrderNotStarted(){
        Mockito.when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isStarted()).thenReturn(false);
        assertThrows(ResponseStatusException.class,()->packageOrderService.endPackageOrder(1254,1));
    }

    @Test
    public void endPackageOrderTestSuccess(){
        Mockito.when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isCanceled()).thenReturn(false);
        Mockito.when(packageOrder.isStarted()).thenReturn(true);
        Mockito.when(packageOrder.getDrop_code()).thenReturn(1234);
        assertEquals("Order ended",packageOrderService.endPackageOrder(1234,1));
    }

    @Test
    public void endPackageOrderTestInvalidCode(){
        Mockito.when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        Mockito.when(packageOrder.isCanceled()).thenReturn(false);
        Mockito.when(packageOrder.isStarted()).thenReturn(true);
        Mockito.when(packageOrder.getDrop_code()).thenReturn(1226);
        assertThrows(ResponseStatusException.class,()->packageOrderService.endPackageOrder(1254,1));
    }
}
