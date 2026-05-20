package com.bluthinkInc.spring_security_project.service.rabbitmq;

public interface EmailService {
    void sendEmail(String to,String subject,String body);
}
