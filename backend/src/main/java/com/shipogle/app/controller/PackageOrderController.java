package com.shipogle.app.controller;

import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.service.PackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PackageOrderController {
    @Autowired
    PackageOrderService packageOrderService;

    @GetMapping("/package/order/getall")
    public List<PackageOrder> getSenderOrders(){
        return packageOrderService.getSenderOrders();
    }

    @GetMapping("package/order/getAllDelivererRouteOrders")
    public List<PackageOrder> getDelivererRouteOrders(@RequestParam int driver_route_id){
        return packageOrderService.getDelivererRouteOrders(Long.valueOf(driver_route_id));
    }

    @PutMapping("/package/order/cancel")
    public String cancelOrder(@RequestBody Map<String,String> req){
        return packageOrderService.cancelOrder(Integer.valueOf(req.get("package_order_id")));
    }

    @PutMapping("package/order/start")
    public String startOrder(@RequestBody Map<String,String> req){
        Integer pickup_code = Integer.valueOf(req.get("pickup_code"));
        Integer order_id = Integer.valueOf(req.get("order_id"));
        return packageOrderService.startPackageOrder(pickup_code,order_id);
    }

    @PutMapping("package/order/end")
    public String endOrder(@RequestBody Map<String,String> req){
        return packageOrderService.endPackageOrder(Integer.valueOf(req.get("drop_code")),Integer.valueOf(req.get("order_id")));
    }

    @PutMapping("package/order/recordPayment")
    public String recordPayment(@RequestBody Map<String,String> req){
        return packageOrderService.recordPayment(Integer.valueOf(req.get("package_order_id")));
    }

}
