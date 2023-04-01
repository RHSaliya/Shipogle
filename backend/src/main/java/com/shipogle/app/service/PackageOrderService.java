package com.shipogle.app.service;

import com.shipogle.app.model.Package;
import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.model.PackageRequest;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.PackageOrderRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

import static com.shipogle.app.utility.Const.RANDOM_UPPER_BOUND;

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
        String pickup_code = String.format("%4d",random.nextInt(RANDOM_UPPER_BOUND));
        String dop_code = String.format("%4d",random.nextInt(RANDOM_UPPER_BOUND));
        packageOrder.setPaymentStatus(Integer.valueOf(0)); //Set payment status 0 indicates payment is not done
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found");
        if(order.isCanceled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already canceled");
        if(order.isStarted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot cancel order");
        order.setCanceled(true);
        order.setPaymentStatus(Integer.valueOf(-1));
        packageOrderRepo.save(order);

        return "order is canceled";
    }

    public List<PackageOrder> getSenderOrders(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user_email = auth.getPrincipal().toString();
            User user = userRepo.getUserByEmail(user_email);

            return packageOrderRepo.getAllBySender(user);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public List<PackageOrder> getDelivererRouteOrders(Long driver_route_id){
        try {
            List<PackageOrder> orders = packageOrderRepo.getAllByDriverRoute_Id(driver_route_id);
            return orders;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public String recordPayment(Integer package_order_id){
        try {
            PackageOrder order = packageOrderRepo.getById(package_order_id);
            order.setPaymentStatus(Integer.valueOf(1));
            packageOrderRepo.save(order);
            return "Payment is recorded";
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public String startPackageOrder(int pickup_code,int order_id){
        PackageOrder order = packageOrderRepo.getById(order_id);

        if(!order.isCanceled() && Integer.valueOf(order.getPickup_code()) == pickup_code){
            order.setStarted(true);
            packageOrderRepo.save(order);
            return "Order started";
        }
//        return "Unable to start the order";
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to start the order");
    }

    public String endPackageOrder(int drop_code,int order_id){
        PackageOrder order = packageOrderRepo.getById(order_id);

        if(isAbleToEndOrder(order,drop_code)){
            order.setDelivered(true);
            packageOrderRepo.save(order);
            return "Order ended";
        }
//        return "Unable to end the order";
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to end the order");
    }

    private boolean isAbleToEndOrder(PackageOrder order, int drop_code){
        boolean active_order = !order.isCanceled() && order.isStarted();
        return active_order && Integer.valueOf(order.getDrop_code()) == drop_code;
    }
}
