package com.shipogle.app.service;

import com.shipogle.app.model.*;
import com.shipogle.app.model.Package;
import com.shipogle.app.repository.PackageOrderRepository;
import com.shipogle.app.repository.PackageRepository;
import com.shipogle.app.repository.PackageRequestRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class PackageRequestService {

    @Autowired
    PackageRequestRepository packageRequestRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PackageRepository packageRepo;

    @Autowired
    PackageOrderRepository packageOrderRepo;


    public String sendRequest(Map<String,String> req){
        try{
            int request_count = packageRequestRepo.countAllBy_package_IdAndDeliverer_Id(Integer.valueOf(req.get("package_id")),Integer.valueOf(req.get("deliverer_id")));
            if(request_count == 0){
                PackageRequest packageRequest = new PackageRequest();

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String user_email = auth.getPrincipal().toString();

                User sender = userRepo.getUserByEmail(user_email);
                User deliverer = userRepo.getUserById(Integer.valueOf(req.get("deliverer_id")));

                packageRequest.setStatus("requested");
                packageRequest.setSender(sender);
                packageRequest.setDeliverer(deliverer);
                packageRequest.setAksPrice(Float.valueOf(req.get("ask_price")));

                Package p = packageRepo.getPackageById(Integer.valueOf(req.get("package_id")));
                packageRequest.set_package(p);

                if(sender==null || deliverer==null || p==null)
                    return "Invalid request";

                packageRequestRepo.save(packageRequest);
            }else {
                return "Already requested";
            }

            return "Request sent";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String acceptRequest(Integer package_request_id){
        try{
            PackageOrder packageOrder = new PackageOrder();
            Random random = new Random();

            PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);

            packageOrder.set_package(packageRequest.get_package());
            packageOrder.setDeliverer(packageRequest.getDeliverer());
            packageOrder.setSender(packageRequest.getSender());
            String pickup_code = String.format("%4d",random.nextInt(10000));
            String dop_code = String.format("%4d",random.nextInt(10000));

            packageOrder.setPickup_code(Integer.valueOf(pickup_code));
            packageOrder.setDrop_code(Integer.valueOf(dop_code));

            packageOrderRepo.save(packageOrder);

            return "Request accepted";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
