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

    @GetMapping("/package/order/getall")
    public List<PackageOrder> getSenderOrders(){
        return packageOrderService.getSenderOrders();
    }

    @PutMapping("/package/order/cancel")
    public String cancelOrder(@RequestBody Map<String,String> req){
        return packageOrderService.cancelOrder(Integer.valueOf(req.get("package_order_id")));
    }
}
