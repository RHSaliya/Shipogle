package com.shipogle.app.controller;

import com.shipogle.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import com.shipogle.app.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    /**
     * @author Nandkumar Kadivar
     * Register user
     * @param user user.
     * @return String response message.
     */
    @PostMapping("/register")
    public String registerNewUser(@RequestBody User user) {
        return authService.register(user);
    }

    /**
     * @author Nandkumar Kadivar
     * User login
     * @param json json request.
     * @return String jwt token.
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> json) {
        long start = System.currentTimeMillis();
        String res = authService.login(json.get("email"), json.get("password"));
        System.out.println("Time difference: "+ (System.currentTimeMillis() - start));
        return res;
    }

    /**
     * @author Nandkumar Kadivar
     * Change password
     * @param json json request.
     * @return String response message.
     */
    @PostMapping("/changepassword")
    public String changePassword(@RequestBody Map<String, String> json) {
        return authService.resetPassword(json.get("token"), json.get("password"));
    }

    /**
     * @author Nandkumar Kadivar
     * Forgot password
     * @param json json request.
     * @return String response message.
     */
    @PostMapping("/forgotpassword")
    public String forgotPassword(HttpServletRequest request, @RequestBody Map<String, String> json) {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        return authService.forgotPassword(json.get("email"));
    }

    /**
     * @author Nandkumar Kadivar
     * Email verification
     * @param code string verification code.
     * @param id int user id.
     * @return String response message.
     */
    @GetMapping("/verification")
    public String emailVerification(@RequestParam("code") String code, @RequestParam("id") int id) {
        return authService.verifyEmail(code, id);
    }

    /**
     * @author Rahul Saliya
     * Email verification
     * @param id int user id.
     * @return String response message.
     */
    @GetMapping("/user")
    public String getUser(@RequestParam("id") int id) {
        System.out.println("id = " + id);
        return authService.getUser(id).toString();
    }

    /**
     * @author Rahul Saliya
     * User information
     * @param token string jwt token.
     * @return User user object.
     */
    @GetMapping("/user_info")
    public User getUserInfo(@RequestHeader("Authorization") String token) {
        return authService.getUserInfo(token);
    }

    /**
     * @author Rahul Saliya
     * Email verification
     * @param token string jwt token.
     * @param user user object
     * @return String response message.
     */
    @PutMapping("/user")
    public String updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        return authService.updateUser(token, user);
    }
}
