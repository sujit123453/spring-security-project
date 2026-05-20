package com.bluthinkInc.spring_security_project.service.rabbitmq.consumer;

import com.bluthinkInc.spring_security_project.dto.EmailEvent;

public interface EmailConsumer {
    void consume(EmailEvent event);
}
