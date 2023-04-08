package com.shipogle.app.service;

import com.shipogle.app.model.DriverRoute;
import com.shipogle.app.model.Issue;
import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.IssueRepository;
import com.shipogle.app.repository.PackageOrderRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {
    @Autowired
    IssueRepository issueRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PackageOrderRepository packageOrderRepo;

    public String postIssue(Integer package_order_id, String description) {
        try {
            Issue issue = new Issue();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user_email = auth.getPrincipal().toString();

            User user = userRepo.getUserByEmail(user_email);

            Issue currentIssue = issueRepo.getIssueByUser(user);

            boolean isOrderIdMatching = false;
            if (currentIssue != null)
                isOrderIdMatching = currentIssue.getPackageOrder().getId().equals(package_order_id);
            if (currentIssue != null && isOrderIdMatching)
                return "Issue Already registered";

            PackageOrder packageOrder = packageOrderRepo.getPackageOrderById(package_order_id);

            issue.setUser(user);
            issue.setPackageOrder(packageOrder);
            issue.setDescription(description);

            issueRepo.save(issue);

        } catch (Exception e) {
            return e.getMessage();
        }
        return "Issue registered";
    }

    public List<Issue> getAllIssues() {
        try {
            return issueRepo.findAll();
        } catch (Exception e) {
            return null;
        }
    }
}
