package com.shipogle.app.controller;

import com.shipogle.app.model.PackageRequest;
import com.shipogle.app.service.PackageRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PackageRequestController {
    @Autowired
    PackageRequestService packageRequestService;

//    @PostMapping("package/request/send")
//    public String sendPackageRequest(@RequestBody PackageRequest packageRequest){
//        return packageRequestService.sendRequest(packageRequest);
//    }

    @PostMapping("package/request/send")
    public String sendPackageRequest(@RequestBody Map<String,String> req){
        return packageRequestService.sendRequest(req);
    }

    @PostMapping("package/request/accept")
    public String acceptPackageRequest(@RequestBody Map<String,String> req){
        return packageRequestService.acceptRequest(Integer.parseInt(req.get("package_request_id")));
    }

    @PostMapping("package/request/reject")
    public String rejectPackageRequest(@RequestBody Map<String,String> req){
        return packageRequestService.rejectRequest(Integer.parseInt(req.get("package_request_id")));
    }

    @DeleteMapping("package/request/delete")
    public String unsendPackageRequest(@RequestBody Map<String,String> req){
        return packageRequestService.unsendRequest(Integer.parseInt(req.get("package_request_id")));
    }
}
