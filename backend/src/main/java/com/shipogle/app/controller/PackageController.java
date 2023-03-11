package com.shipogle.app.controller;

import com.shipogle.app.model.Package;
import com.shipogle.app.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackageController {
    @Autowired
    PackageService packageService;

    @PostMapping("/package/create")
    public String createPackage(@RequestBody Package _package){
        return packageService.storePackage(_package);
    }
}
