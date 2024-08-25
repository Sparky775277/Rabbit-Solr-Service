package main.auditoriumupdateservice.services;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class RabbitSender {

    @Value("${request.queue.name}")
    private String queueName;

    @Value("${scheduler.fixedDelay}")
    private long fixedDelay;

    private final RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    @Scheduled(fixedDelayString = "${scheduler.fixedDelay}")
    public void sendRequestForAuditoriums() {
        String requestMessage = "{\"command\": \"getAll\"}";
        MessagePostProcessor messagePostProcessor = msg -> {
            msg.getMessageProperties().setMessageId(UUID.randomUUID().toString());
            return msg;
        };
        logger.info("Request sent to queue: {}", queueName);
        rabbitTemplate.convertAndSend("", queueName, requestMessage, messagePostProcessor);
    }


}
