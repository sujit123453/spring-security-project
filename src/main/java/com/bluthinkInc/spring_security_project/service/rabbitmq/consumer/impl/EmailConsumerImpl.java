package com.bluthinkInc.spring_security_project.service.rabbitmq.consumer.impl;

import com.bluthinkInc.spring_security_project.config.RabbitMQConfig;
import com.bluthinkInc.spring_security_project.dto.EmailEvent;
import com.bluthinkInc.spring_security_project.service.rabbitmq.EmailService;
import com.bluthinkInc.spring_security_project.service.rabbitmq.consumer.EmailConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumerImpl implements EmailConsumer {

    private final EmailService emailService;
    public EmailConsumerImpl(EmailService emailService){
        this.emailService = emailService;
    }
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(EmailEvent event) {
        System.out.println("Receiving message from queue");
        emailService.sendEmail(
                event.getEmail(),
                "Welcome!",
                "Registration successfully..."
        );
        System.out.println("Email sent successfully");
    }
}
