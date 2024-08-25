package main.apiemulatorservice.services;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Service
@Setter
public class RabbitReceiverAndSender {

    private static final Logger log = LoggerFactory.getLogger(RabbitReceiverAndSender.class);
    @Value("${response.queue.name}")
    private String response_queue;

    private final RabbitTemplate rabbitTemplate;
    private final List<String> responses;
    private int currentResponseIndex = 0;

    public RabbitReceiverAndSender(RabbitTemplate rabbitTemplate,
                                   @Value("classpath:responses/response1.json") Resource response1,
                                   @Value("classpath:responses/response2.json") Resource response2,
                                   @Value("classpath:responses/response3.json") Resource response3) throws IOException {
        this.rabbitTemplate = rabbitTemplate;
        this.responses = List.of(
                new String(Files.readAllBytes(response1.getFile().toPath())),
                new String(Files.readAllBytes(response2.getFile().toPath())),
                new String(Files.readAllBytes(response3.getFile().toPath()))
        );
    }

    @RabbitListener(queues = "${request.queue.name}")
    public void handleRequest(String message) {
        if (message.contains("\"command\": \"getAll\"") && message.length() == 21) {

            MessagePostProcessor messagePostProcessor = msg -> {
                msg.getMessageProperties().setMessageId(UUID.randomUUID().toString());
                return msg;
            };

            log.info("Got request message from request queue!");
            log.info("Send response message to response queue!");

            Message responseMessage = new Message(getNextResponse().getBytes(), new MessageProperties());
            responseMessage.getMessageProperties().setMessageId(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(response_queue, responseMessage, messagePostProcessor);
        }
    }

    private String getNextResponse() {
        String response = responses.get(currentResponseIndex);
        currentResponseIndex = (currentResponseIndex + 1) % responses.size();
        return response;
    }

}
