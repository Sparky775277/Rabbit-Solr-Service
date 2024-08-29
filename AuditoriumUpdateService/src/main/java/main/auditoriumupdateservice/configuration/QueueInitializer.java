package main.auditoriumupdateservice.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueInitializer {

    private final RabbitAdmin rabbitAdmin;
    private final Queue queue;

    public QueueInitializer(RabbitAdmin rabbitAdmin, Queue queue) {
        this.rabbitAdmin = rabbitAdmin;
        this.queue = queue;
    }

    @PostConstruct
    public void declareQueue() {
        rabbitAdmin.declareQueue(queue);
    }
}
