package br.com.fatec.frete.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class RepublishMQ {
    private static final Logger LOG = LoggerFactory.getLogger(RepublishMQ.class);

    private static final String X_RETRIES_HEADER = "x-retries";

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String queue;
    private final String deadLetter;
    private final String parkingLot;

    public RepublishMQ(
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.request.exchange.producer}") String exchange,
            @Value("${spring.rabbitmq.request.routing-key.producer}") String queue,
            @Value("${spring.rabbitmq.request.dead-letter.producer}") String deadLetter,
            @Value("${spring.rabbitmq.request.parking-lot.producer}") String parkingLot) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.queue = queue;
        this.deadLetter = deadLetter;
        this.parkingLot = parkingLot;
    }

    @Scheduled(cron = "${spring.rabbitmq.listener.time-retry}")
    public void rePublish() {
        LOG.info("Chamada a retentativa");
        List<Message> messages = getQueueMessages(rabbitTemplate, deadLetter);

        messages.forEach(message -> {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);

            if (retriesHeader == null) {
                retriesHeader = 0;
            }

            if (retriesHeader < 3) {
                headers.put(X_RETRIES_HEADER, retriesHeader + 1);
                rabbitTemplate.send(exchange, queue, message);
            } else {
                rabbitTemplate.send(parkingLot, message);
            }
        });
    }

    private List<Message> getQueueMessages(RabbitTemplate rabbitTemplate, String deadLetter) {
        List<Message> messages = new ArrayList<>();
        boolean isNull;
        Message message;

        do {
            message = rabbitTemplate.receive(deadLetter);
            isNull = message != null;

            if (isNull) {
                messages.add(message);
            }
        } while (isNull);

        return messages;
    }

}
