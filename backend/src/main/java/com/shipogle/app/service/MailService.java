package com.shipogle.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.mail.SimpleMailMessage;

@Component
public class MailService extends SimpleMailMessage {

    @Autowired
    JavaMailSender sender;

    public void sendMail(String email){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("shipogleApp@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Verify your email");
        simpleMailMessage.setSubject("Please verify your email address: link");
        simpleMailMessage.setText("Verification mail");
        sender.send(simpleMailMessage);
    }
}
