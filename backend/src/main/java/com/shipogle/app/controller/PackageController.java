package com.shipogle.app.controller;

import com.shipogle.app.model.Package;
import com.shipogle.app.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PackageController {
    @Autowired
    PackageService packageService;

    /**
     * @author Nandkumar Kadivar
     * Create package
     * @param _package Package object.
     * @return integer package id.
     */
    @PostMapping("/package/create")
    public Integer createPackage(@RequestBody Package _package){
        return packageService.storePackage(_package);
    }

    /**
     * @author Nandkumar Kadivar
     * Create package
     * @return List packages.
     */
    @GetMapping("/package/getall")
    public List<Package> getAllPackages(){
        return packageService.getPackages();
    }

    /**
     * @author Nandkumar Kadivar
     * Update package
     * @param _package package object
     * @return string response.
     */
    @PutMapping("/package/update")
    public String updatePackage(@RequestBody Package _package){
        return packageService.updatePackage(_package);
    }
}
