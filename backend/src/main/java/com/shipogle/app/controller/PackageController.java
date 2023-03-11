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

    @PostMapping("/package/create")
    public String createPackage(@RequestBody Package _package){
        return packageService.storePackage(_package);
    }

    @GetMapping("/package/getall")
    public List<Package> getAllPackages(){
        return packageService.getPackages();
    }

}
