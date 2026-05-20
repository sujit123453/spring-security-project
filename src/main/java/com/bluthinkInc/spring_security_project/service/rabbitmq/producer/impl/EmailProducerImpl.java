package com.bluthinkInc.spring_security_project.service.rabbitmq.producer.impl;

import com.bluthinkInc.spring_security_project.config.RabbitMQConfig;
import com.bluthinkInc.spring_security_project.dto.EmailEvent;
import com.bluthinkInc.spring_security_project.service.rabbitmq.producer.EmailProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducerImpl implements EmailProducer {
    private final RabbitTemplate rabbitTemplate;
    public EmailProducerImpl(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendEmailEvent(EmailEvent event) {
        System.out.println("Sending message to rabbitmq");
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}
