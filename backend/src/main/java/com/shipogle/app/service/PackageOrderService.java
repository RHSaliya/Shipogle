package com.shipogle.app.service;

import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.model.PackageRequest;
import com.shipogle.app.repository.PackageOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PackageOrderService {
    @Autowired
    PackageOrderRepository packageOrderRepo;

    public String createPackageOrder(PackageRequest packageRequest){
        PackageOrder packageOrder = new PackageOrder();
        Random random = new Random();

        packageOrder.set_package(packageRequest.get_package());
        packageOrder.setDeliverer(packageRequest.getDeliverer());
        packageOrder.setSender(packageRequest.getSender());
        String pickup_code = String.format("%4d",random.nextInt(10000));
        String dop_code = String.format("%4d",random.nextInt(10000));

        packageOrder.setPickup_code(Integer.valueOf(pickup_code));
        packageOrder.setDrop_code(Integer.valueOf(dop_code));

        packageOrderRepo.save(packageOrder);

        return "order created";
    }
}
