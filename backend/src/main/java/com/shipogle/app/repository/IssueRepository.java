package com.shipogle.app.repository;

import com.shipogle.app.model.Issue;
import com.shipogle.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    Issue getIssueByUser(User user);
//    List<Issue> getAll();
}
