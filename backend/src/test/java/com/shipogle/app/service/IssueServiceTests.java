package com.shipogle.app.service;

import com.shipogle.app.model.Issue;
import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.IssueRepository;
import com.shipogle.app.repository.PackageOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTests {
    @InjectMocks
    IssueServiceImpl issueService;
    @Mock
    Issue issue;
    @Mock
    IssueRepository issueRepo;
    @Mock
    User user;
    @Mock
    UserServiceImpl userService;
    @Mock
    PackageOrder packageOrder;
    @Mock
    PackageOrderRepository packageOrderRepo;

    @Test
    public void postIssueTestAlreadyRaisedIssue(){
        Mockito.when(userService.getLoggedInUser()).thenReturn(user);
        Mockito.when(issueRepo.getIssueByUser(user)).thenReturn(issue);
        Mockito.when(issue.getPackageOrder()).thenReturn(packageOrder);
        Mockito.when(packageOrder.getId()).thenReturn(1);
        assertEquals("Issue Already registered",issueService.postIssue(1,"issue description"));
    }

    @Test
    public void postIssueTestSuccess(){
        Mockito.when(userService.getLoggedInUser()).thenReturn(user);
        Mockito.when(issueRepo.getIssueByUser(user)).thenReturn(issue);
        Mockito.when(issue.getPackageOrder()).thenReturn(packageOrder);
        Mockito.when(packageOrder.getId()).thenReturn(2);
        assertEquals("Issue registered",issueService.postIssue(1,"issue description"));
    }
}
