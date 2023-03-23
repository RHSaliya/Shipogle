package com.shipogle.app.repository;

import com.shipogle.app.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByEmail(String email);

    User findUserByEmail(String email);

    User findByEmail(String email);

    User getUserById(Integer id);
    /*
     * @Query("SELECT u FROM User u WHERE u.user_id in :userIds")
     * List<User> getUserByIds(@Param("userIds") List<Integer> userIds);
     */

}
