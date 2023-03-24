package com.shipogle.app.service;

import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.model.PackageRequest;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.PackageOrderRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PackageOrderService {
    @Autowired
    PackageOrderRepository packageOrderRepo;

    @Autowired
    UserRepository userRepo;

    public String createPackageOrder(PackageRequest packageRequest){
        PackageOrder packageOrder = new PackageOrder();
        Random random = new Random();

        packageOrder.set_package(packageRequest.get_package());
        packageOrder.setDeliverer(packageRequest.getDeliverer());
        packageOrder.setSender(packageRequest.getSender());
        packageOrder.setDriverRoute(packageRequest.getDriverRoute());
        String pickup_code = String.format("%4d",random.nextInt(10000));
        String dop_code = String.format("%4d",random.nextInt(10000));

        packageOrder.setPickup_code(Integer.valueOf(pickup_code));
        packageOrder.setDrop_code(Integer.valueOf(dop_code));

        packageOrderRepo.save(packageOrder);

        return "order created";
    }

    public boolean isPackageOrderExist(Integer package_id){
        PackageOrder order = packageOrderRepo.getBy_package_Id(package_id);
        if(order!=null && order.isCanceled()==false)
            return true;
        return false;
    }

    public String cancelOrder(Integer order_id){
        PackageOrder order = packageOrderRepo.getPackageOrderById(order_id);
        if(order == null)
            return "Order not found";
        if(order.isCanceled())
            return "Already canceled";
        if(order.isStarted())
            return "Cannot cancel order";
        order.setCanceled(true);
        packageOrderRepo.save(order);

        return "order is canceled";
    }

    public List<PackageOrder> getSenderOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user_email = auth.getPrincipal().toString();
        User user = userRepo.getUserByEmail(user_email);

        return packageOrderRepo.getAllBySender(user);
    }

    public String startPackageOrder(int pickup_code,int order_id){
        PackageOrder order = packageOrderRepo.getById(order_id);

        if(!order.isCanceled() && Integer.valueOf(order.getPickup_code()) == pickup_code){
            order.setStarted(true);
            packageOrderRepo.save(order);
            return "Order started";
        }
        return "Unable to start the order";
    }

    public String endPackageOrder(int drop_code,int order_id){
        PackageOrder order = packageOrderRepo.getById(order_id);

        if(!order.isCanceled() && order.isStarted() && Integer.valueOf(order.getDrop_code()) == drop_code){
            order.setDelivered(true);
            packageOrderRepo.save(order);
            return "Order ended";
        }
        return "Unable to end the order";
    }
}
