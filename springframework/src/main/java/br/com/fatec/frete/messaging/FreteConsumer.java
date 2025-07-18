package br.com.fatec.frete.messaging;

import br.com.fatec.frete.entity.enumerable.Status;
import br.com.fatec.frete.messaging.adapter.FreteConsumerAdapter;
import br.com.fatec.frete.messaging.dto.PedidoFreteAmqp;
import br.com.fatec.frete.service.FreteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FreteConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(FreteConsumer.class);
    private static final String X_RETRIES_HEADER = "x-death";

    private final String parkingLot;
    private final FreteService freteService;
    private final RabbitTemplate rabbitTemplate;

    public FreteConsumer(
            FreteService freteService,
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.request.parking-lot.producer}") String parkingLot) {
        this.parkingLot = parkingLot;
        this.freteService = freteService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(
            queues = "${spring.rabbitmq.request.routing-key.producer}",
            containerFactory = "rabbitFactory")
    public void listener(@Payload PedidoFreteAmqp pedido, Message message, @Headers Map<String, Object> headers) {
        try {
            LOG.info("Pedido de frete: {}", pedido);
            freteService.saveStatus(FreteConsumerAdapter.cast(pedido), Status.PROCESSANDO);
        } catch (Exception ex) {
            Long tentativas = getRetryCount(headers);
            LOG.warn("Tentativas atuais: {}", tentativas);
            if (tentativas > 3) {
                rabbitTemplate.send(parkingLot, message);
                freteService.saveStatus(FreteConsumerAdapter.cast(pedido), Status.FALHA);
                return;
            }
            throw new AmqpRejectAndDontRequeueException(ex);
        }
    }

    private Long getRetryCount(Map<String, Object> headers) {
        if (headers.containsKey(X_RETRIES_HEADER)) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) headers.get(X_RETRIES_HEADER);
            if (!list.isEmpty()) {
                return (Long) list.get(0).get("count");
            }
        }
        return 0L;
    }


}
