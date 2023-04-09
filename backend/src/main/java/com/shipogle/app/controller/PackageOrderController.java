package com.shipogle.app.controller;

import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.service.PackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PackageOrderController {
    @Autowired
    PackageOrderService packageOrderService;

    /**
     * @author Nandkumar Kadivar
     * Get all package order for user
     * @return List package orders.
     */
    @GetMapping("/package/order/getall")
    public List<PackageOrder> getSenderOrders(){
        return packageOrderService.getSenderOrders();
    }

    /**
     * @author Nandkumar Kadivar
     * Get all package order for user
     * @return List packages.
     */
    @GetMapping("package/order/getAllDelivererRouteOrders")
    public List<PackageOrder> getDelivererRouteOrders(@RequestParam int driver_route_id){
        return packageOrderService.getDelivererRouteOrders(Long.valueOf(driver_route_id));
    }

    /**
     * @author Nandkumar Kadivar
     * Cancel order
     * @param req request
     * @return string response message.
     */
    @PutMapping("/package/order/cancel")
    public String cancelOrder(@RequestBody Map<String,String> req){
        return packageOrderService.cancelOrder(Integer.valueOf(req.get("package_order_id")));
    }

    /**
     * @author Nandkumar Kadivar
     * Start order
     * @param req request
     * @return string response message.
     */
    @PutMapping("package/order/start")
    public String startOrder(@RequestBody Map<String,String> req){
        Integer pickup_code = Integer.valueOf(req.get("pickup_code"));
        Integer order_id = Integer.valueOf(req.get("order_id"));
        return packageOrderService.startPackageOrder(pickup_code,order_id);
    }

    /**
     * @author Nandkumar Kadivar
     * End order
     * @param req request
     * @return string response message.
     */
    @PutMapping("package/order/end")
    public String endOrder(@RequestBody Map<String,String> req){
        return packageOrderService.endPackageOrder(Integer.valueOf(req.get("drop_code")),Integer.valueOf(req.get("order_id")));
    }

    /**
     * @author Nandkumar Kadivar
     * Record payament of order
     * @param req request
     * @return string response message.
     */
    @PutMapping("package/order/recordPayment")
    public String recordPayment(@RequestBody Map<String,String> req){
        return packageOrderService.recordPayment(Integer.valueOf(req.get("package_order_id")));
    }

}
