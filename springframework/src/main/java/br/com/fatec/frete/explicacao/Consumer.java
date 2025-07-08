package br.com.fatec.frete.explicacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Consumer {
    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(
            queues = "fila.pedido.criado",
            containerFactory = "rabbitFactory")
    public void listener1(@Payload String pedido, @Headers Map<String, Object> headers) {
        LOG.info("Mensagem recebida 1: {}", pedido);
    }

    @RabbitListener(
            queues = "fila.frete.pedidos",
            containerFactory = "rabbitFactory")
    public void listener2(@Payload String pedido, @Headers Map<String, Object> headers) {
        LOG.info("Mensagem recebida 1: {}", pedido);
    }

    @RabbitListener(
            queues = "fila.frete.todos",
            containerFactory = "rabbitFactory")
    public void listener3(@Payload String pedido, @Headers Map<String, Object> headers) {
        LOG.info("Mensagem recebida 2: {}", pedido);
    }

}
