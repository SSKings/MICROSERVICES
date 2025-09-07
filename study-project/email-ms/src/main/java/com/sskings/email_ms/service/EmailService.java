package com.sskings.email_ms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sskings.email_ms.enums.StatusEmail;
import com.sskings.email_ms.model.EmailModel;
import com.sskings.email_ms.repository.EmailRepository;

import jakarta.transaction.Transactional;

@Service
public class EmailService {
    
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (Exception e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            System.out.println("Error sending email: " + e.getMessage());
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
