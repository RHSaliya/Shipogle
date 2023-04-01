package com.shipogle.app.controller;

import com.shipogle.app.model.Issue;
import com.shipogle.app.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class IssueController {
    @Autowired
    IssueService issueService;

    @PostMapping("/issue/post")
    public String postIssue(@RequestBody Map<String,String> req){
        return issueService.postIssue(Integer.valueOf(req.get("package_order_id")),req.get("description"));
    }

    @GetMapping("issue/getall")
    public List<Issue> getAllIssues(){
        return issueService.getAllIssues();
    }
}
