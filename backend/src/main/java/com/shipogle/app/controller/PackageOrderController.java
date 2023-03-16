package com.shipogle.app.controller;

import com.shipogle.app.service.PackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PackageOrderController {
    @Autowired
    PackageOrderService packageOrderService;

    @PutMapping("/package/order/cancel")
    public String cancelOrder(@RequestBody Map<String,String> req){
        return packageOrderService.cancelOrder(Integer.valueOf(req.get("package_order_id")));
    }
}
