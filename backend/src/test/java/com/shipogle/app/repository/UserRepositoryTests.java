package com.shipogle.app.repository;

import com.shipogle.app.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

//    @Test
//    void getUserByEmailTest(){
//        String email = "kadivarnand007@gmail.com";
//        User user = new User();
//        user.setEmail(email);
//        System.out.println(user);
//        repo.save(user);
//        assertEquals(user,repo.getUserByEmail(email));
//    }

}
