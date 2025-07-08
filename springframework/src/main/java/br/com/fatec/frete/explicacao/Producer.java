package br.com.fatec.frete.explicacao;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produzir() {
        String mensagem = "Mensagem enviada";

        MessageProperties props = new MessageProperties();
        props.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

        Message message = new Message(mensagem.getBytes(StandardCharsets.UTF_8), props);

        rabbitTemplate.send("frete.topic", "fila.pedido.criado", message);
    }

}
