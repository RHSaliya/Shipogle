package com.shipogle.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.mail.SimpleMailMessage;

@Component
public class MailServiceImpl extends SimpleMailMessage {

    @Autowired
    JavaMailSender sender;

    /**
     * @author Nandkumar Kadivar
     * Send email to user
     * @param email user email
     * @param subject email subject
     * @param body email body
     * @param link link that needs to be shared in email
     */
    public void sendMail(String email, String subject, String body ,String link){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("shipogleApp@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body+" "+link);
        sender.send(simpleMailMessage);
    }
}
