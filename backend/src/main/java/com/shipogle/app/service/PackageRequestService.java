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

    @Autowired
    PackageOrderService packageOrderService;


    public String sendRequest(Map<String,String> req){
        try{
            int request_count = packageRequestRepo.countAllBy_package_IdAndDeliverer_Id(Integer.valueOf(req.get("package_id")),Integer.valueOf(req.get("deliverer_id")));

            if(packageOrderService.isPackageOrderExist(Integer.valueOf(req.get("package_id"))))
                return "Cannot send request after order creation";

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

    private void changeRequestStatus(Integer package_request_id,String new_status){
        PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);
        packageRequest.setStatus(new_status);
        packageRequestRepo.save(packageRequest);
    }

    private void rejectOtherPackageRequests(Integer package_id){
        List<PackageRequest> requests = packageRequestRepo.getAllBy_package_Id(package_id);
        for (PackageRequest req: requests) {
            req.setStatus("rejected");
            packageRequestRepo.save(req);
        }
    }

    public String acceptRequest(Integer package_request_id){
        try{
            PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);

            if (packageRequest == null || packageRequest.getStatus().equals("rejected") || packageRequest.getStatus().equals("accepted"))
                return "Cannot accept request";

            rejectOtherPackageRequests(packageRequest.get_package().getId());
            changeRequestStatus(package_request_id,"accepted");

            String result = packageOrderService.createPackageOrder(packageRequest);

            if(result.equals("order created"))
                return "Request accepted";
            else
                return "fail to create order";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String rejectRequest(Integer package_request_id){
        try{
            PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);

            if (packageRequest == null || packageRequest.getStatus().equals("rejected"))
                return "Already rejected";

            changeRequestStatus(package_request_id,"rejected");

            return "Request rejected";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String unsendRequest(Integer package_request_id){
        try{

            PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);

            if (packageRequest == null)
                return "No request found";

            if(packageRequest.getStatus().equals("accepted"))
                return "Cannot delete accepted request";

            packageRequestRepo.delete(packageRequest);

            return "Request deleted";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
