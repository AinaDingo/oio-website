package com.inclusiv.oio_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendMail {

    @Autowired
    public JavaMailSender mailSender;

    public void envoyerMail(String to, String subject, String text,String cc) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setCc(cc);

        this.mailSender.send(mailMessage);
    }

    
}
