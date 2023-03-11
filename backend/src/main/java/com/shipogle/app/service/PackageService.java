package com.shipogle.app.service;

import com.shipogle.app.model.Package;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.PackageRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    @Autowired
    PackageRepository packageRepo;

    @Autowired
    UserRepository userRepo;

    public String storePackage(Package courier){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user_email = auth.getPrincipal().toString();
            User user = userRepo.getUserByEmail(user_email);

            courier.setSender(user);
            packageRepo.save(courier);
            return "Package saved";
        }catch (Exception e){
            return "Failed to save package";
        }
    }

    public List<Package> getPackages(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user_email = auth.getPrincipal().toString();
            User user = userRepo.getUserByEmail(user_email);

            List<Package> packages = packageRepo.getAllBySender(user);
            for (Package i:packages) {
                System.out.println(i);
            }

            return packages;
        }catch (Exception e){
            return null;
        }
    }
}
