package com.shipogle.app.service;

import com.shipogle.app.model.Package;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.PackageRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    @Autowired
    PackageRepository packageRepo;

    @Autowired
    UserRepository userRepo;

    public Integer storePackage(Package courier){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user_email = auth.getPrincipal().toString();
            User user = userRepo.getUserByEmail(user_email);

            courier.setSender(user);
            Integer package_id = packageRepo.save(courier).getId();

            return package_id;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
//            return "Failed to save package";
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save package");
        }
    }

    public List<Package> getPackages(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user_email = auth.getPrincipal().toString();
            User user = userRepo.getUserByEmail(user_email);

            List<Package> packages = packageRepo.getAllBySender(user);
//            for (Package i:packages) {
//                System.out.println(i);
//            }

            return packages;
        }catch (Exception e){
//            return null;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public String updatePackage(Package courier){
        try{
            Package p = packageRepo.getPackageById((Integer) courier.getId());
            p.setTitle(courier.getTitle());
            p.setDescription(courier.getDescription());
            p.setHeigth(courier.getHeigth());
            p.setWidth(courier.getWidth());
            p.setLength(courier.getLength());
            p.setPickup_address(courier.getPickup_address());
            p.setDrop_address(courier.getDrop_address());
            packageRepo.save(p);

            return "Package updated";
        }catch (Exception e){
//            return e.getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
