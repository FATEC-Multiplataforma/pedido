package br.com.fatec.frete;

import br.com.fatec.frete.explicacao.Producer;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRabbit
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class FreteApplication implements CommandLineRunner {
    private final Producer producer;

    public FreteApplication(Producer producer) {
        this.producer = producer;
    }

    public static void main(String[] args) {
        SpringApplication.run(FreteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        producer.produzir();
    }
}