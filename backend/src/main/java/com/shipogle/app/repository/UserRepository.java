package com.shipogle.app.repository;
import com.shipogle.app.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User getUserByEmail(String email);
    User findUserByEmail(String email);

}
