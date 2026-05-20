package com.bluthinkInc.spring_security_project.service.rabbitmq.producer;

import com.bluthinkInc.spring_security_project.dto.EmailEvent;

public interface EmailProducer {
    void sendEmailEvent(EmailEvent event);
}
