package com.shipogle.app.service;

import com.shipogle.app.model.*;
import com.shipogle.app.model.Package;
import com.shipogle.app.repository.PackageOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PackageOrderServiceTests {
    @InjectMocks
    PackageOrderServiceImpl packageOrderService;
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
//        Package package1 = new Package();
//        PackageRequest packageRequest1 = new PackageRequest();
//        packageRequest1.set_package(package1);
//        packageRequest1.setDeliverer(user);
//        packageRequest1.setSender(user);
//        packageRequest1.setDriverRoute(driverRoute);
//
////        Mockito.lenient().when(packageOrderRepo.save(packageOrder)).thenReturn(packageOrder);
//
//        assertEquals("order created",packageOrderService.createPackageOrder(packageRequest1));
//    }

    @Test
    public void cancelOrderTestOrderNotFound(){
        when(packageOrderRepo.getPackageOrderById(10)).thenReturn(null);
        assertThrows(ResponseStatusException.class,()->packageOrderService.cancelOrder(10));
    }

    @Test
    public void cancelOrderTestAlreadyCanceled(){
        when(packageOrderRepo.getPackageOrderById(10)).thenReturn(packageOrder);
        when(packageOrder.isCanceled()).thenReturn(true);
        assertThrows(ResponseStatusException.class,()->packageOrderService.cancelOrder(10));
    }

    @Test
    public void cancelOrderTestAlreadyStarted(){
        when(packageOrderRepo.getPackageOrderById(10)).thenReturn(packageOrder);
        when(packageOrder.isStarted()).thenReturn(true);
        assertThrows(ResponseStatusException.class,()->packageOrderService.cancelOrder(10));
    }

    @Test
    public void cancelOrderTestSuccess(){
        when(packageOrderRepo.getPackageOrderById(10)).thenReturn(packageOrder);
        when(packageOrder.isStarted()).thenReturn(false);
        when(packageOrder.isStarted()).thenReturn(false);
        Mockito.lenient().when(packageOrderRepo.save(packageOrder)).thenReturn(packageOrder);
        assertEquals("order is canceled",packageOrderService.cancelOrder(10));
    }

    @Test
    public void getSenderOrdersTestSuccess(){
        List<PackageOrder> orders = new ArrayList<>();
        when(userService.getLoggedInUser()).thenReturn(user);
        when(packageOrderRepo.getAllBySender(user)).thenReturn(orders);

        assertEquals(orders,packageOrderService.getSenderOrders());
    }

    @Test
    public void getSenderOrdersTestUserNotFound(){
        when(userService.getLoggedInUser()).thenThrow(UsernameNotFoundException.class);
        assertThrows(ResponseStatusException.class,()->packageOrderService.getSenderOrders());
    }

    @Test
    public void getDelivererRouteOrdersTestSuccess(){
        List<PackageOrder> orders = new ArrayList<>();
        when(packageOrderRepo.getAllByDriverRoute_Id(1L)).thenReturn(orders);
        assertEquals(orders,packageOrderService.getDelivererRouteOrders(1L));
    }

    @Test
    public void getDelivererRouteOrdersTestInvalidRoute(){
        when(packageOrderRepo.getAllByDriverRoute_Id(1L)).thenThrow(RuntimeException.class);
        assertThrows(ResponseStatusException.class,()->packageOrderService.getDelivererRouteOrders(1L));
    }

    @Test
    public void recordPaymentTestInvalidOrder(){
        when(packageOrderRepo.getById(1)).thenThrow(RuntimeException.class);
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
        when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        when(packageOrder.isCanceled()).thenReturn(true);
        assertThrows(ResponseStatusException.class,()->packageOrderService.startPackageOrder(1254,1));
    }

    @Test
    public void startPackageOrderTestSuccess(){
        when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        when(packageOrder.isCanceled()).thenReturn(false);
        when(packageOrder.getPickup_code()).thenReturn(1234);
        assertEquals("Order started",packageOrderService.startPackageOrder(1234,1));
    }

    @Test
    public void startPackageOrderTestInvalidCode(){
        when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        when(packageOrder.isCanceled()).thenReturn(false);
        when(packageOrder.getPickup_code()).thenReturn(1226);
        assertThrows(ResponseStatusException.class,()->packageOrderService.startPackageOrder(1254,1));
    }

    @Test
    public void endPackageOrderTestOrderNotStarted(){
        when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        when(packageOrder.isStarted()).thenReturn(false);
        assertThrows(ResponseStatusException.class,()->packageOrderService.endPackageOrder(1254,1));
    }

    @Test
    public void endPackageOrderTestSuccess(){
        when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        when(packageOrder.isCanceled()).thenReturn(false);
        when(packageOrder.isStarted()).thenReturn(true);
        when(packageOrder.getDrop_code()).thenReturn(1234);
        assertEquals("Order ended",packageOrderService.endPackageOrder(1234,1));
    }

    @Test
    public void endPackageOrderTestInvalidCode(){
        when(packageOrderRepo.getById(1)).thenReturn(packageOrder);
        when(packageOrder.isCanceled()).thenReturn(false);
        when(packageOrder.isStarted()).thenReturn(true);
        when(packageOrder.getDrop_code()).thenReturn(1226);
        assertThrows(ResponseStatusException.class,()->packageOrderService.endPackageOrder(1254,1));
    }

    @Test
    public void isPackageOrderExistTestPositive(){
        when(packageOrderRepo.getBy_package_Id(1)).thenReturn(packageOrder);
        assertTrue(packageOrderService.isPackageOrderExist(1));
    }

    @Test
    public void isPackageOrderExistTestNegative(){
        when(packageOrderRepo.getBy_package_Id(1)).thenReturn(null);
        assertFalse(packageOrderService.isPackageOrderExist(1));
    }
}
