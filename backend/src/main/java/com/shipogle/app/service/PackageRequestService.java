package com.shipogle.app.service;

import com.shipogle.app.model.Package;
import com.shipogle.app.model.PackageRequest;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.PackageRepository;
import com.shipogle.app.repository.PackageRequestRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PackageRequestService {

    @Autowired
    PackageRequestRepository packageRequestRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PackageRepository packageRepo;

    public String sendRequest(Map<String,String> req){
        try{
            PackageRequest packageRequest = new PackageRequest();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user_email = auth.getPrincipal().toString();

            User sender = userRepo.getUserByEmail(user_email);
            User deliverer = userRepo.getUserById(Integer.valueOf(req.get("deliverer_id")));

            packageRequest.setSender(sender);
            packageRequest.setDeliverer(deliverer);
            packageRequest.setAksPrice(Float.valueOf(req.get("ask_price")));

            Package p = packageRepo.getPackageById(Integer.valueOf(req.get("package_id")));
            packageRequest.set_package(p);

            packageRequestRepo.save(packageRequest);

            return "Request sent";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
