package com.ruis.dailymood.microservices;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // Variables for professional email format
    private final String greeting = "Dear family,";
    private final String signature = "Sincerely,\nDailymood Team";

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String subject, String body, String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        String fullMessage = greeting + "\n\n" + body + "\n\n" + signature;
        message.setText(fullMessage);
        mailSender.send(message);
    }

}
