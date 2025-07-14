package br.com.fatec.frete;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${spring.redis.host}")
    String host;
    @Value("${spring.redis.port}")
    String port;

    public static void main(String[] args) {
        SpringApplication.run(FreteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Connecting to Redis at Host: " + host + " Port:" + port);
    }
}