//package br.com.fatec.frete.explicacao;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.util.StdDateFormat;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//@Configuration
//public class RabbitMQTopicConfig {
//
//    @Bean
//    SimpleRabbitListenerContainerFactory rabbitFactory(ConnectionFactory cf) {
//        SimpleRabbitListenerContainerFactory f = new SimpleRabbitListenerContainerFactory();
//        f.setConnectionFactory(cf);
//        f.setMessageConverter(jacksonConverter());
//        return f;
//    }
//
//    @Bean
//    RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
//        RabbitTemplate rt = new RabbitTemplate(cf);
//        rt.setMessageConverter(jacksonConverter());
//        return rt;
//    }
//
//    @Bean
//    Jackson2JsonMessageConverter jacksonConverter() {
//        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
//                .modules(new JavaTimeModule())
//                .dateFormat(new StdDateFormat())
//                .build();
//        return new Jackson2JsonMessageConverter(mapper);
//    }
//
//    @Bean
//    public TopicExchange freteExchange() {
//        return new TopicExchange("frete.topic");
//    }
//
//    @Bean
//    Queue filaPedidoCriado() {
//        return new Queue("fila.pedido.criado");
//    }
//
//    @Bean
//    Queue filaPedidos() {
//        return new Queue("fila.frete.pedidos");
//    }
//
//    @Bean
//    Queue filaTudoFrete() {
//        return new Queue("fila.frete.todos");
//    }
//
//    @Bean
//    Binding bindingPedidoCriado(Queue filaPedidoCriado, TopicExchange freteExchange) {
//        return BindingBuilder.bind(filaPedidoCriado)
//                .to(freteExchange)
//                .with("frete.pedido.criado");
//    }
//
//    @Bean
//    Binding bindingPedidos(@Qualifier("filaPedidos") Queue fila, TopicExchange freteExchange) {
//        return BindingBuilder.bind(fila)
//                .to(freteExchange)
//                .with("frete.pedido.#");
//    }
//
//    @Bean
//    Binding bindingTudo(Queue filaTudoFrete, TopicExchange freteExchange) {
//        return BindingBuilder.bind(filaTudoFrete)
//                .to(freteExchange)
//                .with("frete.#");
//    }
//}
