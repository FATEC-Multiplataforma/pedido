package br.com.fatec.frete.messaging;

import br.com.fatec.frete.messaging.dto.PedidoFreteAmqp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FreteConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(FreteConsumer.class);

    @RabbitListener(queues = "${spring.rabbitmq.request.routing-key.producer}",
            containerFactory = "rabbitFactory")
    public void receberPedido(PedidoFreteAmqp pedido) {
        try {
            LOG.info("Pedido de frete: {}", pedido);
            throw new AmqpRejectAndDontRequeueException("Error");
        } catch (Exception ex) {
            LOG.info("Erro no processamento do pedido: {}", ex.getMessage());
            throw new AmqpRejectAndDontRequeueException(ex);
        }
    }

}
